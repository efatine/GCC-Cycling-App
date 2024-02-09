package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChooseAccount extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    EditText confirmPasswordText;

    Button adminRegisterButton;
    Button clubRegisterButton;
    Button participantRegisterButton;

    Button backToLoginButton;

    AccountDatabaseHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

        usernameText = findViewById(R.id.registerUsername);
        passwordText = findViewById(R.id.registerPassword);
        confirmPasswordText = findViewById(R.id.registerPasswordConfirm);
        participantRegisterButton = findViewById(R.id.registerParticipant);
        adminRegisterButton = findViewById(R.id.registerAdmin);
        clubRegisterButton = findViewById(R.id.registerClub);
        backToLoginButton = findViewById(R.id.backToLogin);

        dbHandler = new AccountDatabaseHandler(this);

        adminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add admin register method
                registerUser("Admin");
            }
        });

        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseAccount.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        participantRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add participant register method
                registerUser("Participant");
            }
        });

        clubRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add club register method
                registerUser("Club owner");
            }
        });
    }

    private void registerUser(String userRole) {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();

        if (username.isEmpty()) {
            showToast("Please enter a username!");
            return;
        }

        if (password.isEmpty()) {
            showToast("Please enter a password!");
            return;
        }

        if (!confirmPassword.equals(password)) {
            showToast("Passwords do not match!");
            return;
        }

        if (dbHandler.checkUserExists(username)){
            showToast("User already exists!");
            return;
        }

        boolean isRegistered = dbHandler.addUser(username, password, userRole);
        if (isRegistered) {
            showToast("Success! Registered successfully as " + userRole);
            navigateToMainActivity();
        } else {
            showToast("Unknown Registration Error!");
        }
    }

    private void showToast(String message) {
        Toast.makeText(ChooseAccount.this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(ChooseAccount.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}