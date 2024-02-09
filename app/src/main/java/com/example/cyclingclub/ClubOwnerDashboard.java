package com.example.cyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ClubOwnerDashboard extends AppCompatActivity {

    private TextView clubNameTextView;
    private TextView clubDescriptionTextView;
    private TextView clubLocationTextView;
    private TextView clubContactNumberTextView;
    private TextView clubSocialMediaTextView;
    private EditText clubNameEditText;

    Button backButton;



    // Database handler
    private ClubProfileDatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_owner_dashboard); // Make sure to use the correct layout file name

        // Initialize views
        clubNameEditText = findViewById(R.id.clubName);
        clubNameTextView = findViewById(R.id.clubNameTitle);
        clubDescriptionTextView = findViewById(R.id.clubDescription);
        clubLocationTextView = findViewById(R.id.clubLocation);
        clubContactNumberTextView = findViewById(R.id.clubContactNumber);
        clubSocialMediaTextView = findViewById(R.id.clubSocialMediaLinks);
        backButton = findViewById(R.id.clubDashboardBackButton);

        // Initialize the database handler
        dbHandler = new ClubProfileDatabaseHandler(this);

        // Load and display club information
        loadAndDisplayClubInfo();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubOwnerDashboard.this, ClubOwnerWelcome.class);
                intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(intent);
            }
        });
    }

    private void loadAndDisplayClubInfo() {
        // Assuming you have a method to get the latest or specific club profile.
        // Replace 'yourClubId' with the actual ID of the club or modify as per your retrieval logic.
        //System.out.println("test: " + clubNameEditText.getText().toString());
        String clubName = getIntent().getStringExtra("USERNAME");
        ClubProfileDatabaseHandler.ClubProfile clubProfile = dbHandler.getClubProfile(clubName);

        // Set the text in TextViews
        if(clubProfile != null) {
            clubNameTextView.setText(clubProfile.getName());
            clubDescriptionTextView.setText(clubProfile.getDescription());
            clubLocationTextView.setText(clubProfile.getLocation());
            clubContactNumberTextView.setText(clubProfile.getContactNumber());
            clubSocialMediaTextView.setText(clubProfile.getSocialMedia());
        }
    }

    // Other methods for the activity
}
