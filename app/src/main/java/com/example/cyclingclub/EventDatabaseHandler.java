package com.example.cyclingclub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

// New database handler class that is the same as the account DatabaseHandler but modified for events
public class EventDatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "EventInfo.db";
    private static final String TABLE_EVENT = "events";
    private static final String COL_ID = "id";
    private static final String COL_EVENT_TYPE = "event_type";
    private static final String COL_EVENT_NAME = "event_name";
    private static final String COL_INFO = "event_info";
    private static final String COL_REQS = "event_requirements";
    private static final String COL_PARTICIPANTS = "participants";
    private static final String COL_REGISTERED_PARTICIPANT = "registeredParticipant";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_EVENT_NAME + " TEXT,"
            + COL_EVENT_TYPE + " TEXT,"
            + COL_INFO + " TEXT,"
            + COL_REQS + " TEXT,"
            + COL_REGISTERED_PARTICIPANT + " TEXT,"
            + COL_PARTICIPANTS + " TEXT" + ")";
    public EventDatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(sqLiteDatabase);
    }

    public String fetchEventType(String eventName) {
        SQLiteDatabase database = getReadableDatabase();
        String[] columns = {COL_EVENT_TYPE};
        String selection = COL_EVENT_NAME + "=?";
        String[] selectionArgs = {eventName};

        try (Cursor cursor = database.query(TABLE_EVENT, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(COL_EVENT_TYPE));
                return type;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String fetchEventDetails(String eventName) {
        SQLiteDatabase database = getReadableDatabase();
        String[] columns = {COL_INFO};
        String selection = COL_EVENT_NAME + "=?";
        String[] selectionArgs = {eventName};

        try (Cursor cursor = database.query(TABLE_EVENT, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String details = cursor.getString(cursor.getColumnIndex(COL_INFO));
                return details;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String fetchEventRequirements(String eventName) {
        SQLiteDatabase database = getReadableDatabase();
        String[] columns = {COL_REQS};
        String selection = COL_EVENT_NAME + "=?";
        String[] selectionArgs = {eventName};

        try (Cursor cursor = database.query(TABLE_EVENT, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String requirements = cursor.getString(cursor.getColumnIndex(COL_REQS));
                return requirements;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertEvent(String name, String type, String details, String requirements) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_EVENT_NAME, name);
        values.put(COL_EVENT_TYPE, type);
        values.put(COL_INFO, details);
        values.put(COL_REQS, requirements);

        long rowId = database.insert(TABLE_EVENT, null, values);
        database.close();

        return rowId != -1;
    }

    public ArrayList<String> getAllInfo() {
        ArrayList<String> eventNames = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {COL_EVENT_NAME};

        try (Cursor cursor = database.query(TABLE_EVENT, columns, null, null, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex(COL_EVENT_NAME));
                    eventNames.add(eventName);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return eventNames;
    }

    public boolean removeEvent(String eventName) {
        SQLiteDatabase database = this.getWritableDatabase();

        String whereClause = COL_EVENT_NAME + "=?";
        String[] whereArgs = {eventName};

        int rowsDeleted = database.delete(TABLE_EVENT, whereClause, whereArgs);
        database.close();

        return rowsDeleted > 0;
    }

    public boolean updateEventData(String currentName, String newName, String newType, String newDetails, String newRequirements) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues updatedValues = new ContentValues();
        updatedValues.put(COL_EVENT_NAME, newName);
        updatedValues.put(COL_EVENT_TYPE, newType);
        updatedValues.put(COL_INFO, newDetails);
        updatedValues.put(COL_REQS, newRequirements);

        String whereClause = COL_EVENT_NAME + "=?";
        String[] whereArgs = {currentName};

        int rowsAffected = database.update(TABLE_EVENT, updatedValues, whereClause, whereArgs);

        database.close();

        return rowsAffected > 0;
    }


    public boolean doesEventNameExist(String eventName) {
        SQLiteDatabase database = getReadableDatabase();

        String[] columns = {COL_EVENT_NAME};
        String selection = COL_EVENT_NAME + "=?";
        String[] selectionArgs = {eventName};

        try (Cursor cursor = database.query(TABLE_EVENT, columns, selection, selectionArgs, null, null, null)) {
            if (cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addParticipant(String eventName, String username) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COL_EVENT_NAME, eventName);
            values.put(COL_PARTICIPANTS, username);

            long result = db.insert(TABLE_EVENT, null, values);
            return result != -1;
        } catch (SQLiteException e) {
            // Handle the SQLiteException as needed (e.g., log or throw)
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeParticipant(String eventName, String username) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int result = db.delete(TABLE_EVENT,
                    COL_EVENT_NAME + "=? AND " + COL_PARTICIPANTS + "=?",
                    new String[]{eventName, username});
            return result > 0;
        } catch (SQLiteException e) {
            // Handle the SQLiteException as needed (e.g., log or throw)
            e.printStackTrace();
            return false;
        }
    }








}
