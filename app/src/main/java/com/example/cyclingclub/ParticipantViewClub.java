package com.example.cyclingclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ParticipantViewClub extends AppCompatActivity {

    private ArrayAdapter<String> adaptClub;
    private ArrayList<String> arrayListClubs;
    AccountDatabaseHandler dbHandler;
    ListView listClubs;
    EditText searchClubNameEditText;
    static String user;
    Button dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view_club);

        listClubs = findViewById(R.id.listOfClubs);
        searchClubNameEditText = findViewById(R.id.searchClubName);
        arrayListClubs = new ArrayList<>();

        dbHandler = new AccountDatabaseHandler(this);
        AccountDatabaseHandler db = new AccountDatabaseHandler(this);

        arrayListClubs = db.getUsers("Club owner");

        adaptClub = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListClubs);

        listClubs.setAdapter(adaptClub);
        dashboard = findViewById(R.id.ParticipantDashboardButton);

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(ParticipantViewClub.this, ParticipantDashboard.class);
                startActivity(viewClubs);
            }
        });

        listClubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String select = arrayListClubs.get(i);
                displayAlertDialog(select);

            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    private void displayAlertDialog(String selectedItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ParticipantViewClub.this);
        alertDialogBuilder.setTitle(selectedItem);




    }

    private void showToast(String message) {
        Toast.makeText(ParticipantViewClub.this, message, Toast.LENGTH_SHORT).show();
    }
}