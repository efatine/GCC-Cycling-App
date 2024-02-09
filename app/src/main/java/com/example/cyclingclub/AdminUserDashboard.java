package com.example.cyclingclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminUserDashboard extends AppCompatActivity {

    Button backButton;
    Button viewParticipants;
    Button viewClubMembers;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_user_dashboard);

        backButton = findViewById(R.id.dashboardBackButton);
        viewParticipants = findViewById(R.id.participantButton);
        viewClubMembers = findViewById(R.id.clubUsersButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent errorIntent = new Intent(AdminUserDashboard.this, AdminWelcomeActivity.class);
                errorIntent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(errorIntent);
            }
        });





        viewParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewParticipants = new Intent(AdminUserDashboard.this, AdminViewParticipants.class);
                startActivity(viewParticipants);
            }
        });

        viewClubMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(AdminUserDashboard.this, AdminViewClubs.class);
                startActivity(viewClubs);
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

}
