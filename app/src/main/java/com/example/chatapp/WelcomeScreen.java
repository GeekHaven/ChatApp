package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class WelcomeScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private Button mSignInWithGoogle;
    private FirebaseAuth mAuth;
    private Button mSignInWithEmail;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        mAuth = FirebaseAuth.getInstance();
        mSignInWithEmail = findViewById(R.id.sign_in_with_email);
        mSignInWithGoogle = findViewById(R.id.sign_in_with_google);

        mSignInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        createRequest();

        mSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.d("google-auth-error",e.toString());
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"SignIn Failed :)",Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseHandler.registrationCheck(mAuth.getUid(),WelcomeScreen.this);
                            LocalBroadcastManager.getInstance(WelcomeScreen.this).registerReceiver(receiver,new IntentFilter("custom-action-local-broadcast"));
                        }
                        else{
                            Toast.makeText(WelcomeScreen.this, "Sorry Google Authentication Failed!" +
                                    "\nPlease check your internet connectivity", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        Intent intent2;
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = "";
            message += intent.getStringExtra("validation");
            Log.i(".,.,.,.<><><>< message",(message));
            if(message.equals("true")) {
                intent2 = new Intent(WelcomeScreen.this,MainActivity.class);
                Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show();
                startActivity(intent2);
            }else {
                Toast.makeText(context, "Not Registered", Toast.LENGTH_SHORT).show();
                intent2 = new Intent(WelcomeScreen.this,RegistrationActivity.class);
                startActivity(intent2);
            }
        }
    };

}