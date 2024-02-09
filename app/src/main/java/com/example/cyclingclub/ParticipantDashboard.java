package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParticipantDashboard extends AppCompatActivity {

    Button viewClubMembers;
    Button viewEvents;

    Button logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_dashboard);

        viewClubMembers = findViewById(R.id.searchClubButton);
        viewEvents = findViewById(R.id.searchEventButton);

        logOut = findViewById(R.id.LogOutButton);

        viewClubMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(ParticipantDashboard.this, ParticipantViewClub.class);
                startActivity(viewClubs);
            }
        });

        viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(ParticipantDashboard.this, ParticipantEventView.class);
                startActivity(viewClubs);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(ParticipantDashboard.this, MainActivity.class);
                startActivity(viewClubs);
            }
        });
    }
}