package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHandler{


    FirebaseHandler(){
        // empty constructor
    }

    public static void registrationCheck(final String idToken , final Context context){

        readData(new FirebaseCallBack() {
            @Override
            public void CallBack(ArrayList<String> s) {

                boolean check = false;
                for(String ite : s){

                    if(ite.equals(idToken)){
                        check = true;
                        break;
                    }
                }

                Intent intent = new Intent("custom-action-local-broadcast");
                intent.putExtra("validation",String.valueOf(check));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    public static void readData(final FirebaseCallBack firebaseCallBack){

        final ArrayList<String> uid = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    uid.add(snapshot1.getKey());
                }

                firebaseCallBack.CallBack(uid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface FirebaseCallBack {
        void CallBack(ArrayList<String> s);
    }


}