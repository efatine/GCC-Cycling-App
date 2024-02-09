package com.example.cyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeTextView;
    AccountDatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeTextView = (TextView) findViewById(R.id.welcomeText);
        dbHandler = new AccountDatabaseHandler(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String role = intent.getStringExtra("ROLE");

        welcomeTextView.setText("Welcome, " + username + "!\nRole: " + role);


    }
}
