package com.example.cyclingclub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClubProfileDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "ClubProfileManager";
    //////////////////////////////////////////////////////////////////////////
    // Club profile table name
    private static final String TABLE_CLUB_PROFILE = "clubProfile";
    // Club Profile Table Columns names
    private static final String COL_CLUB_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_LOCATION = "location";
    private static final String COL_CONTACT_NUMBER = "contactNumber";
    private static final String COL_SOCIAL_MEDIA = "socialMedia";
    private static final String COL_CLUB_OWNER = "clubOwner";
    //////////////////////////////////////////////////////////////////////////
    private static final String TABLE_FEEDBACK = "clubFeedback";
    private static final String COL_FEEDBACK_ID = "id";
    private static final String COL_FEEDBACK_VALUE = "feedbackValue";
    private static final String COL_FEEDBACK_COMMENT = "feedbackComment";
    private static final String COL_FEEDBACK_USERNAME = "feedbackUsername";
    //////////////////////////////////////////////////////////////////////////

    public ClubProfile loadClubProfile;

    private static final String CREATE_CLUB_TABLE = "CREATE TABLE " + TABLE_CLUB_PROFILE + "("
            + COL_CLUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NAME + " TEXT,"
            + COL_DESCRIPTION + " TEXT,"
            + COL_CONTACT_NUMBER + " TEXT,"
            + COL_LOCATION + " TEXT,"
            + COL_SOCIAL_MEDIA + " TEXT,"
            + COL_CLUB_OWNER + " TEXT," // Assuming COL_CLUB_OWNER is the column for club_owner
            + "UNIQUE (" + COL_NAME + ", " + COL_CLUB_OWNER + ") ON CONFLICT REPLACE" // Define UNIQUE constraint across club_name and club_owner
            + ")";

    private static final String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + TABLE_FEEDBACK + "("
            + COL_FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_CLUB_ID + " INTEGER,"
            + COL_FEEDBACK_VALUE + " INTEGER,"
            + COL_FEEDBACK_COMMENT + " TEXT,"
            + COL_FEEDBACK_USERNAME + " TEXT,"
            + "FOREIGN KEY (" + COL_CLUB_ID + ") REFERENCES " + TABLE_CLUB_PROFILE + "(" + COL_CLUB_ID + ")" + ")";


    public ClubProfileDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public String getLoadClubProfileName() {
        return loadClubProfile.getName();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLUB_TABLE);
        db.execSQL(CREATE_FEEDBACK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUB_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);

        // Create tables again
        onCreate(db);
    }

    // Adding new club profile
    /*public void addClubProfile(ClubProfile clubProfile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, clubProfile.getName());
        values.put(KEY_DESCRIPTION, clubProfile.getDescription());
        values.put(KEY_LOCATION, clubProfile.getLocation());
        values.put(KEY_CONTACT_NUMBER, clubProfile.getContactNumber());

        // Inserting Row
        db.insert(TABLE_CLUB_PROFILE, null, values);
        db.close(); // Closing database connection
    }*/

    public void saveClubDetails(ClubProfile clubProfile, Context context, String clubOwner) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, clubProfile.getName());
        values.put(COL_DESCRIPTION, clubProfile.getDescription());
        values.put(COL_CONTACT_NUMBER, clubProfile.getContactNumber());
        values.put(COL_LOCATION, clubProfile.getLocation());
        values.put(COL_SOCIAL_MEDIA, clubProfile.getSocialMedia());
        values.put(COL_CLUB_OWNER, clubOwner);

        long id = -1;
        try {
            int rows = db.update(TABLE_CLUB_PROFILE, values, COL_CLUB_OWNER + " = ?", new String[]{clubOwner});
            if (rows == 0) {
                id = db.insert(TABLE_CLUB_PROFILE, null, values);
            }

            if (rows > 0 || id != -1) {
                showToast("Profile saved!", context);
                loadClubProfile = new ClubProfile(clubProfile.getName(), clubProfile.getDescription(), clubProfile.getContactNumber(),
                        clubProfile.getLocation(), clubProfile.getSocialMedia());
            } else {
                showToast("Failed to create profile", context);
            }
        } catch (Exception e) {
            showToast("Error creating profile", context);
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void addRating(int clubId, ClubFeedback rating) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COL_CLUB_ID, clubId);
            values.put(COL_FEEDBACK_VALUE, rating.getClubRating());
            values.put(COL_FEEDBACK_COMMENT, rating.getCommentFeedback());
            values.put(COL_FEEDBACK_USERNAME, rating.getParticipantUsername());
            db.insert(TABLE_FEEDBACK, null, values);
        } catch (SQLiteException e) {
            // Handle the exception according to your application's needs
            e.printStackTrace();
        }
    }




    // Getting single club profile
    public ClubProfile getClubProfile(String clubOwnerString) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {
                COL_NAME,
                COL_DESCRIPTION,
                COL_CONTACT_NUMBER,
                COL_LOCATION,
                COL_SOCIAL_MEDIA,
                COL_CLUB_OWNER,
        };

        Cursor cur = db.query(TABLE_CLUB_PROFILE, cols, COL_CLUB_OWNER + " = ?", new String[]{clubOwnerString}, null, null, null);
        ClubProfile profile = null;
        if (cur.moveToFirst()) {
            @SuppressLint("Range") String clubName = cur.getString(cur.getColumnIndex(COL_NAME));
            @SuppressLint("Range") String description = cur.getString(cur.getColumnIndex(COL_DESCRIPTION));
            @SuppressLint("Range") String contactNumber = cur.getString(cur.getColumnIndex(COL_CONTACT_NUMBER));
            @SuppressLint("Range") String location = cur.getString(cur.getColumnIndex(COL_LOCATION));
            @SuppressLint("Range") String socialMedia = cur.getString(cur.getColumnIndex(COL_SOCIAL_MEDIA));
            profile = new ClubProfile(clubName, description, location, contactNumber, socialMedia);
        }
        cur.close();
        db.close();
        return profile;
    }


   /* public int updateClubProfile(ClubProfile clubProfile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, clubProfile.getName());
        values.put(KEY_DESCRIPTION, clubProfile.getDescription());
        values.put(KEY_LOCATION, clubProfile.getLocation());
        values.put(KEY_CONTACT_NUMBER, clubProfile.getContactNumber());

        // Updating row
        return db.update(TABLE_CLUB_PROFILE, values, KEY_ID + " = ?",
                new String[]{clubProfile.getName()});
    }

    // Delete a club profile
    public void deleteClubProfile(ClubProfile clubProfile) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLUB_PROFILE, COL_NAME + " = ?",
                new String[]{clubProfile.getName()});
        db.close();
    }*/

    // Get all club profiles
   /* public List<ClubProfile> getAllClubProfiles() {
        List<ClubProfile> clubProfileList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CLUB_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(KEY_ID);
                    int nameIndex = cursor.getColumnIndex(KEY_NAME);
                    int descriptionIndex = cursor.getColumnIndex(KEY_DESCRIPTION);
                    int locationIndex = cursor.getColumnIndex(KEY_LOCATION);
                    int contactNumberIndex = cursor.getColumnIndex(KEY_CONTACT_NUMBER);
                    int socialMediaIndex = cursor.getColumnIndex(KEY_SOCIAL_MEDIA);

                    if (idIndex >= 0 && nameIndex >= 0 && descriptionIndex >= 0 && locationIndex >= 0
                            && contactNumberIndex >= 0 && socialMediaIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        String description = cursor.getString(descriptionIndex);
                        String location = cursor.getString(locationIndex);
                        String contactNumber = cursor.getString(contactNumberIndex);
                        String socialMedia = cursor.getString(socialMediaIndex);

                        ClubProfile clubProfile = new ClubProfile(name, description, location, contactNumber, socialMedia);
                        clubProfileList.add(clubProfile);
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        // return club profile list
        return clubProfileList;
    } */

    private void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public List<ClubFeedback> getClubFeedback(int clubId) {
        List<ClubFeedback> ratings = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_FEEDBACK,
                new String[]{COL_FEEDBACK_VALUE, COL_FEEDBACK_COMMENT, COL_FEEDBACK_USERNAME},
                COL_CLUB_ID + "=?",
                new String[]{String.valueOf(clubId)},
                null, null, null)) {

            if (cursor.moveToFirst()) {
                do {
                   @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex(COL_FEEDBACK_VALUE));
                   @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex(COL_FEEDBACK_COMMENT));
                   @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COL_FEEDBACK_USERNAME));
                    ratings.add(new ClubFeedback(rating, comment, username));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            // Handle the SQLiteException as needed (e.g., log or throw)
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return ratings;
    }



    // Here you can add more methods like updateClubProfile(), deleteClubProfile(), getAllClubProfiles() etc.

    // ClubProfile model class
    public static class ClubProfile {
        private int id;
        private String name;
        private String description;
        private String location;
        private String contactNumber;
        private String socialMedia;

        // Constructor
        public ClubProfile(String name, String description, String location, String contactNumber, String socialMedia) {
            this.name = name;
            this.description = description;
            this.location = location;
            this.contactNumber = contactNumber;
            this.socialMedia = socialMedia;
        }

        // Getter methods


        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getLocation() {
            return location;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public String getSocialMedia() {
            return socialMedia;
        }


        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public void setSocialMedia(String socialMedia) {
            this.socialMedia = socialMedia;
        }

    }



}