package com.example.cyclingclub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccountDatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "UserInfo.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_ID = "id";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROLE = "role";

    private static final String TABLE_EVENT = "events";
    private static final String COL_EVENT_ID = "id";
    private static final String COL_EVENT_TYPE = "event_type";
    private static final String COL_EVENT_NAME = "event_name";
    private static final String COL_INFO = "event_info";
    private static final String COL_REQS = "event_requirements";
    private static final String USER_TABLE_INIT = "CREATE TABLE " + TABLE_USER + "(" + COL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_USERNAME + " TEXT,"
            + COL_PASSWORD + " TEXT,"
            + COL_ROLE + " TEXT" + ")";

    public AccountDatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_INIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean addUser(String user, String pass, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues ctValues = new ContentValues();
        ctValues.put(COL_USERNAME, user);
        ctValues.put(COL_PASSWORD, pass);
        ctValues.put(COL_ROLE, role);

        long res = db.insert(TABLE_USER, null, ctValues);
        db.close();

        if(res == -1)
            return false;
        else
            return true;
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=?", new String[]{username});
        if(cur.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkUserPassword(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{user, pass});
        if (cur.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getUserRole(String username) {
        SQLiteDatabase database = getWritableDatabase();

        String[] columns = {COL_ROLE};
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = database.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        String role = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            }
            cursor.close();
        }

        return role;
    }

    public boolean removeUser(String user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int res = sqLiteDatabase.delete(TABLE_USER, COL_USERNAME + "=?", new String[]{user});
        sqLiteDatabase.close();

        if (res > 0)
            return true;

        return false;
    }

    public ArrayList<String> getUsers(String role) {
        ArrayList<String> users = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {COL_USERNAME, COL_PASSWORD};

        Cursor cur = sqLiteDatabase.query(TABLE_USER, columns, null, null, null, null, null);

        try {
            while (cur.moveToNext()) {
                @SuppressLint("Range") String user = cur.getString(cur.getColumnIndex(COL_USERNAME));
              //  @SuppressLint("Range") String password = cur.getString(cur.getColumnIndex(COL_PASSWORD));

                if (this.getUserRole(user).equals(role) && !users.contains(user)) {
                    users.add(user);
                }
            }
        } finally {
            cur.close();
            sqLiteDatabase.close();
        }

        return users;
    }
}

