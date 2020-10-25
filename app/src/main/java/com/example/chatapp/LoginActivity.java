package com.example.chatapp;

import androidx.annotation.NonNull;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText email;
    EditText password;
    Button emailsignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.editTextTextPersonName);
        password=findViewById(R.id.editTextTextPassword);
        emailsignin=findViewById(R.id.button2);

        emailsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userdata=email.getText().toString();
                String userpass=password.getText().toString();
                if (TextUtils.isEmpty(userdata) | TextUtils.isEmpty(userpass))
                {
                    Toast.makeText(LoginActivity.this, "Fill all feilds", Toast.LENGTH_LONG).show();
                }
                else {
                    if( !Patterns.EMAIL_ADDRESS.matcher(userdata).matches()){
                        email.setError("Enter Valid email");
                        email.requestFocus();
                        return;
                    }
                    else if (password.length()<6)
                    {
                        password.setError("Password Length should atleast be 6");
                        password.requestFocus();
                        return;
                    }
                    else {
                        mAuth.signInWithEmailAndPassword(userdata, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this,"User Signed In",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}