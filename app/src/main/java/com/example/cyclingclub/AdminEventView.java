package com.example.cyclingclub;

import android.accounts.Account;
import android.app.AlertDialog;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminEventView extends AppCompatActivity {

    ListView eventList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> eventListArray;
    private boolean eventEdited = false;

    private static String newEventName;
    private static String newEventType;
    private static String newEventDetails;
    private Button backButton;

    EventDatabaseHandler eventDb;
    AccountDatabaseHandler accountDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        backButton = findViewById(R.id.backButton);

        eventEdited = false;
        eventDb = new EventDatabaseHandler(this);
        accountDb = new AccountDatabaseHandler(this);
        eventList = findViewById(R.id.eventList);
        eventListArray = new ArrayList<>();
        EventDatabaseHandler eventDBHandler = new EventDatabaseHandler(this);
        SQLiteDatabase database = eventDBHandler.getReadableDatabase();

        eventListArray = eventDBHandler.getAllInfo();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventListArray);
        eventList.setAdapter(arrayAdapter);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String chosenItem = eventListArray.get(i);
                displayAlertDialog(chosenItem);
                newEventType = eventDb.fetchEventType(chosenItem);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountDb.getUserRole(getIntent().getStringExtra("USERNAME")).equals("Club owner")) {
                    Intent intent = new Intent(AdminEventView.this, ClubOwnerWelcome.class);
                    intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                    startActivity(intent);
                    finish();
                }  else {
                    Intent intent = new Intent(AdminEventView.this, AdminWelcomeActivity.class);
                    intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                    startActivity(intent);
                    finish();
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




    private void displayAlertDialog(String selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminEventView.this);

        // Set the dialog title
        builder.setTitle(selectedItem);

        // Set the positive button for modifying the event
        builder.setPositiveButton("Modify Event", (dialogInterface, i) -> {
            handleModifyEvent(selectedItem);
        });

        // Set the negative button for removing the event
        builder.setNegativeButton("Remove Event", (dialogInterface, i) -> {
            handleRemoveEvent(selectedItem);
        }).create();

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        alertDialog.show();
    }

    private void handleModifyEvent(String selectedItem) {
        newEventName = selectedItem;
        eventEdited = true;

        // Create an Intent for modifying the event
        Intent modifyEvent = new Intent(AdminEventView.this, AdminCreateEvent.class);
        modifyEvent.putExtra("Modifying event", eventEdited);
        modifyEvent.putExtra("Event Type", eventDb.fetchEventType(newEventName));

        // Start the modification activity
        eventDb.removeEvent(selectedItem);
        startActivity(modifyEvent);
    }

    private void handleRemoveEvent(String selectedItem) {
        // Remove the event from the database
        eventDb.removeEvent(selectedItem);

        // Remove the event from the list
        eventListArray.remove(selectedItem);

        // Show a toast message
        showToast("'" + selectedItem + "' event has been removed!");

        // Update the event list view
        eventList.setAdapter(arrayAdapter);
    }


    private void showToast(String message) {
        Toast.makeText(AdminEventView.this, message, Toast.LENGTH_SHORT).show();
    }

}
