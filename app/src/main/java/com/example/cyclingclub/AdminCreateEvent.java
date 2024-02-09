package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class AdminCreateEvent extends AppCompatActivity {

    private RadioGroup eventTypeRadioGroup;
    private RadioGroup ageRangeRadioGroup;
    private RadioGroup paceRadioGroup;
    private RadioGroup levelRadioGroup;
    private Button createEventButton;
    private RadioButton selectedAgeRange;
    private RadioButton selectedEventType;
    private RadioButton selectedLevel;
    private RadioButton selectedPace;
    private EditText nameText;

    private String name;
    private String type;
    private String infoLevel;
    private String infoPace;
    private String reqs;

    EventDatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Initialize UI components
        eventTypeRadioGroup = findViewById(R.id.eventTypeRadioGroup);
        ageRangeRadioGroup = findViewById(R.id.AgeRangeRadioGroup);
        paceRadioGroup = findViewById(R.id.paceRadioGroup);
        levelRadioGroup = findViewById(R.id.levelRadioGroup);
        createEventButton = findViewById(R.id.createEventButtonMain);
        nameText = findViewById(R.id.nameText);
        dbHandler = new EventDatabaseHandler(this);

        // Set click listener for the create event button
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    // Handle button click event here
                    // You can access selected radio buttons from the RadioGroups
                    int selectedEventTypeId = eventTypeRadioGroup.getCheckedRadioButtonId();
                    selectedEventType = findViewById(selectedEventTypeId);

                    int selectedAgeRangeId = ageRangeRadioGroup.getCheckedRadioButtonId();
                    selectedAgeRange = findViewById(selectedAgeRangeId);

                    int selectedLevelId = levelRadioGroup.getCheckedRadioButtonId();
                    selectedLevel = findViewById(selectedLevelId);

                    int selectedPaceId = paceRadioGroup.getCheckedRadioButtonId();
                    selectedPace = findViewById(selectedPaceId);

                    type = selectedEventType.getText().toString().trim();
                    reqs = selectedAgeRange.getText().toString().trim();
                    infoLevel = selectedLevel.getText().toString().trim();
                    infoPace = selectedPace.getText().toString().trim();
                    name = nameText.getText().toString().trim();

                    // Print info to console to verify nullpointerexceptions
                    System.out.println("type: " + type);
                    System.out.println("reqs: " + reqs);
                    System.out.println("infolevel: " + infoLevel);
                    System.out.println("infopace: " + infoPace);
                    System.out.println("name: " + name);

                    if (type.isEmpty() || reqs.isEmpty() || infoLevel.isEmpty() || infoPace.isEmpty() || name.isEmpty()) {
                        if (dbHandler.doesEventNameExist(name)) {
                            showToast("Event name already exists");
                            return;
                        }
                        showToast("Please fill out all criteria");
                        return;
                    } else {
                        dbHandler.insertEvent(name, type, infoLevel + "\n" + infoPace, reqs);
                        Intent adminIntent = new Intent(AdminCreateEvent.this, AdminWelcomeActivity.class);
                        adminIntent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                        startActivity(adminIntent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent errorIntent = new Intent(AdminCreateEvent.this, AdminWelcomeActivity.class);
                    startActivity(errorIntent);
                    showToast("Error Occurred! Check LogCat!");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void showToast(String message) {
        Toast.makeText(AdminCreateEvent.this, message, Toast.LENGTH_SHORT).show();
    }


}