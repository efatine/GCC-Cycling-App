package com.example.cyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClubOwnerWelcome extends AppCompatActivity {

    Button createProfile;
    Button viewEvents;
    Button backButton;


    protected void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_welcome_club);

        createProfile = findViewById(R.id.clubWelcomeCreateProfile);
        viewEvents = findViewById(R.id.clubWelcomeViewEvents);
        backButton = findViewById(R.id.clubWelcomeBackButton);

        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubOwnerWelcome.this, ClubOwner.class);
                intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(intent);
            }
        });

        viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubOwnerWelcome.this, AdminEventView.class);
                intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubOwnerWelcome.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}
