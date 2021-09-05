package com.example.simplifi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        name = (TextView) findViewById(R.id.Name);
        email = (TextView) findViewById(R.id.Email);
        token = (TextView) findViewById(R.id.Token);
        Intent intent = getIntent();
        String str_name = intent.getStringExtra("message_name");
        String str_email = intent.getStringExtra("message_email");
        String str_token = intent.getStringExtra("message_token");

//        Toast.makeText(SecondActivity.this, str, Toast.LENGTH_SHORT).show();
//        String str_name = intent.getStringExtra("message_name");
//        Toast.makeText(SecondActivity.this, "END_AT thw", Toast.LENGTH_SHORT).show();
//        Toast.makeText(SecondActivity.this, str_name, Toast.LENGTH_SHORT).show();
//        Toast.makeText(SecondActivity.this, str_email, Toast.LENGTH_SHORT).show();
////        String str_email = intent.getStringExtra("message_email");
//        Toast.makeText(SecondActivity.this, str_token, Toast.LENGTH_SHORT).show();

        name.setText(str_name);
        email.setText(str_email);
//        token.setText(str_token);
    }
}