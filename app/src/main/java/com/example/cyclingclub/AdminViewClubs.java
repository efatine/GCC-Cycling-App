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
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminViewClubs extends AppCompatActivity {
    Button backButton;
    private ArrayAdapter<String> adaptClub;
    private ArrayList<String> arrayListClubs;
    AccountDatabaseHandler dbHandler;
    ListView listClubs;
    static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_clubs);
        backButton = findViewById(R.id.viewClubsBackBtn);
        listClubs = findViewById(R.id.listOfClubs);

        arrayListClubs = new ArrayList<>();

        dbHandler = new AccountDatabaseHandler(this);
        AccountDatabaseHandler db = new AccountDatabaseHandler(this);

        arrayListClubs = db.getUsers("Club owner");

        adaptClub = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListClubs);

        listClubs.setAdapter(adaptClub);

        listClubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String select = arrayListClubs.get(i);
                displayAlertDialog(select);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(AdminViewClubs.this, AdminUserDashboard.class);
                backIntent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(backIntent);
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminViewClubs.this);
        alertDialogBuilder.setTitle(selectedItem);
        alertDialogBuilder.setPositiveButton("Remove User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user = selectedItem;
                dbHandler.removeUser(user);
                arrayListClubs.remove(user);
                showToast(user + " has been removed!");
                listClubs.setAdapter(adaptClub);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(AdminViewClubs.this, message, Toast.LENGTH_SHORT).show();
    }


}
