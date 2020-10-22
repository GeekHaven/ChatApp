package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        TextView descriptionText = findViewById(R.id.get_started_text_description);
        descriptionText.setText(Html.fromHtml(getColoredString()));

        Button getStatedBtn = findViewById(R.id.get_started_btn);
        getStatedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    private String getColoredString(){
        String res = "<font color=#2F7FB9>Vibsey</font> lets you to get in contact with your closed ones. Message contacts, start free video or voice calls, and hop on a conversation with one person or a group.";
        return res;
    }

}