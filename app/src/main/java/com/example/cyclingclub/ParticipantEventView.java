package com.example.cyclingclub;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.metrics.Event;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class ParticipantEventView extends AppCompatActivity {
    ListView eventList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> eventListArray;
    private boolean eventEdited = false;

    private static String newEventName;
    private static String newEventType;
    private static String newEventDetails;
    private Button dashboard;

    EventDatabaseHandler eventDb;
    AccountDatabaseHandler accountDb;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_event_view);
        dashboard = findViewById(R.id.ParticipantDashboardButton);


        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubs = new Intent(ParticipantEventView.this, ParticipantDashboard.class);
                startActivity(viewClubs);
            }
        });

    }


    }