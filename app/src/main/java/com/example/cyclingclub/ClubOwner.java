package com.example.cyclingclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClubOwner extends AppCompatActivity {

    private EditText clubNameEditText;
    private EditText clubDescriptionEditText;
    private EditText clubLocationEditText;
    private EditText clubContactNumberEditText;

    private EditText clubSocialMediaEditText;
    private Button saveProfileButton;

    private Button backButton;


    // Database handler
    private ClubProfileDatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_owner);

        // Initialize views
        clubNameEditText = findViewById(R.id.clubName);
        clubDescriptionEditText = findViewById(R.id.clubDescription);
        clubLocationEditText = findViewById(R.id.clubLocation);
        clubContactNumberEditText = findViewById(R.id.clubContactNumber);
        saveProfileButton = findViewById(R.id.saveProfileButton);
        clubSocialMediaEditText = findViewById(R.id.clubSocialMedia);
        backButton = findViewById(R.id.clubOwnerBackButton);

        // Initialize the database handler
        dbHandler = new ClubProfileDatabaseHandler(this);

        loadAllInfo();

        // Set up listener for the save profile button
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubOwner.this, ClubOwnerWelcome.class);
                intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(intent);
            }
        });


    }

    private void saveProfile() {
        // Get values from EditTexts
        String name = clubNameEditText.getText().toString().trim();
        String description = clubDescriptionEditText.getText().toString().trim();
        String location = clubLocationEditText.getText().toString().trim();
        String contactNumber = clubContactNumberEditText.getText().toString().trim();
        String socialMedia = clubSocialMediaEditText.getText().toString().trim();

        boolean isValid = true;

        if (socialMedia.isEmpty() || !socialMedia.contains(".com")) {
            showToast("Social Media field empty or link doesn't contain '.com'!");
            isValid = false;
        }

        if (!isValidPhoneNumber(contactNumber)) {
            showToast("Phone number is invalid!");
            isValid = false;
        }

        if (contactNumber.isEmpty()) {
            clubContactNumberEditText.setError("Phone number is mandatory.");
            isValid = false;
        } else if (!isValidPhoneNumber(contactNumber)) {
            isValid = false;
        }

        if (description.isEmpty()) {
            isValid = false;
            showToast("Empty description");
        }

        if (location.isEmpty()) {
            isValid = false;
            showToast("Empty location");
        }

        // Create ClubProfile object
        if (isValid) {
            ClubProfileDatabaseHandler.ClubProfile clubProfile = new ClubProfileDatabaseHandler.ClubProfile(
                    name, description, location, contactNumber, socialMedia
            );
            // Save to database
            dbHandler.saveClubDetails(clubProfile, ClubOwner.this, getIntent().getStringExtra("USERNAME"));
            Intent intent = new Intent(ClubOwner.this, ClubOwnerDashboard.class);
            intent.putExtra("CLUB_NAME", name);
            intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
            startActivity(intent);
        }
    }

    public void loadAllInfo() {
        String owner = getIntent().getStringExtra("USERNAME");
        ClubProfileDatabaseHandler.ClubProfile clubProfile = dbHandler.getClubProfile(owner);

        if (clubProfile != null) {
            clubDescriptionEditText.setText(clubProfile.getDescription());
            clubSocialMediaEditText.setText(clubProfile.getSocialMedia());
            clubContactNumberEditText.setText(clubProfile.getContactNumber());
            clubLocationEditText.setText(clubProfile.getLocation());
            clubNameEditText.setText(clubProfile.getName());
        }
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String digits = phoneNumber.replaceAll("\\D+", "");
        boolean isValid = digits.length() >= 10;

        if (!isValid) {
            clubContactNumberEditText.setError("Phone number must be at least 10 digits.");
        }

        return isValid;
    }
    private void showToast(String message) {
        Toast.makeText(ClubOwner.this, message, Toast.LENGTH_SHORT).show();
    }

    // Other methods for the activity
}
