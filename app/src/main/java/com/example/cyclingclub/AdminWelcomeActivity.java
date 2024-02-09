package com.example.cyclingclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class AdminWelcomeActivity extends AppCompatActivity {

    TextView welcomeText;
    AccountDatabaseHandler dbHandler;
    Button logOut;

    private Button EventCreateButton;
    private Button UserAccountDeleteButton;
    private Button CheckEventsButton;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        welcomeText = (TextView) findViewById(R.id.admin_welcomeText);
        EventCreateButton = (Button) findViewById(R.id.EventCreateButton);
        CheckEventsButton = (Button) findViewById(R.id.CheckEventsButton);
        dbHandler = new AccountDatabaseHandler(this);
        logOut = findViewById(R.id.adminLogOutButton);


        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        welcomeText.setText("Welcome, " + username + "!\nRole: " + "Admin");

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(AdminWelcomeActivity.this, MainActivity.class);
                startActivity(viewClubs);
            }
        });
    }





    public void onEventClick(View view){
        Intent eventIntent = new Intent(AdminWelcomeActivity.this , AdminCreateEvent.class);
        eventIntent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
        startActivity(eventIntent);
        finish();
    }

    public void onUserDashboardClick(View view) {
        Intent eventIntent = new Intent(AdminWelcomeActivity.this , AdminUserDashboard.class);
        startActivity(eventIntent);
        finish();
    }

    public void onViewEventClick(View view) {
        Intent eventView = new Intent(AdminWelcomeActivity.this, AdminEventView.class);
        eventView.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
        startActivity(eventView);
        finish();
    }



    private void showToast(String message) {
        Toast.makeText(AdminWelcomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
