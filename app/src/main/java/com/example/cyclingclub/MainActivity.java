package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button createAccountButton;
    Button loginButton;
    AccountDatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = (EditText)findViewById(R.id.mainUsernameText);
        passwordText = findViewById(R.id.mainPasswordText);
        loginButton = findViewById(R.id.mainLoginButton);
        createAccountButton = findViewById(R.id.creataAccountButton);

        dbHandler = new AccountDatabaseHandler(this);

        //force creation of admin account incase database does not get exported with app
        if (dbHandler.addUser("admin", "admin", "Admin") && dbHandler.addUser("gccadmin", "GCCRocks!", "Club owner") && dbHandler.addUser("cyclindaddict", "cyclingIsLife!", "Participant"))
            Log.d("Database", "Account has been created successfully!");
        else
            Log.d("Database", "Account either already exists or an error occurred!");

        // Change status bar color
        // SOURCE: https://www.geeksforgeeks.org/how-to-change-the-color-of-status-bar-in-an-android-app/
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    public void OnLoginButton(View view) {
        // this code is for login button (it will send the user to Login Activity)
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(username.isEmpty() && password.isEmpty()) {
            showToast("Username and password fields are empty!");
            return;
        }

        if(username.isEmpty()) {
            showToast("Username is empty!");
            return;
        }

        if (password.isEmpty()) {
            showToast("Password is empty!");
            return;
        }

        if(!dbHandler.checkUserExists(username)) {
            showToast("User does not exist!");
            return;
        }

        boolean isValidPassword = dbHandler.checkUserPassword(username, password);
        if (isValidPassword) {
            String userRole = dbHandler.getUserRole(username);
            launchWelcomeActivity(username, userRole);
        } else {
            showToast("Invalid password!");
        }

    }

    private void launchWelcomeActivity(String username, String userRole) {
        System.out.println(userRole);
        if (userRole.equals("Admin")) {
            Intent welcomeIntent = new Intent(MainActivity.this, AdminWelcomeActivity.class);
            welcomeIntent.putExtra("USERNAME", username);
            welcomeIntent.putExtra("ROLE", userRole);
            startActivity(welcomeIntent);
        }else if (userRole.equals("Club owner")){
            Intent welcomeIntent = new Intent (MainActivity.this, ClubOwnerWelcome.class);
            welcomeIntent.putExtra("USERNAME", username);
            welcomeIntent.putExtra("ROLE", userRole);
            startActivity(welcomeIntent);
        }else {
            Intent welcomeIntent = new Intent(MainActivity.this, ParticipantDashboard.class);
            welcomeIntent.putExtra("USERNAME", username);
            welcomeIntent.putExtra("ROLE", userRole);
            startActivity(welcomeIntent);
        }
        finish();
    }



    public void OnCreateAccountButton(View view) {
        // this code is for create account button (it will send the user to choose kind of account Activity)
        Intent intent = new Intent(getApplicationContext(), ChooseAccount.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}