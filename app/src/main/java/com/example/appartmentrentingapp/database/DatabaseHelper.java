package com.example.appartmentrentingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appartmentrentingapp.model.ApartmentModel;
import com.example.appartmentrentingapp.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "apartment_app";
    // Columns for the Users table
    private static final String TABLE_USERS = "users_table";
    private static final String KEY_ID_USER = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";

    // Columns for the Apartments table
    private static final String TABLE_APARTMENTS = "apartments_table";
    private static final String KEY_ID_APARTMENT = "apartment_id";
    private static final String KEY_IMAGE = "image";

    private static final String KEY_LOCATION = "location";
    private static final String KEY_PRICE = "price";
    private static final String KEY_OWNER_NAME = "owner_name";
    private static final String KEY_OWNER_PHONE = "owner_phone";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_STATUS = "status";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_BOOKING_DATE = "booking_date";
    private static final String KEY_YEARS = "years";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the Users table
        db.execSQL(" CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID_USER + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_PHONE + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL);"
        );

        // Create the Apartments table
        db.execSQL(" CREATE TABLE " + TABLE_APARTMENTS + " (" +
                KEY_ID_APARTMENT + " INTEGER PRIMARY KEY, " +
                KEY_IMAGE + " TEXT NOT NULL, " +
                KEY_LOCATION + " TEXT NOT NULL, " +
                KEY_PRICE + " TEXT NOT NULL, " +
                KEY_OWNER_NAME + " TEXT NOT NULL, " +
                KEY_OWNER_PHONE + " TEXT NOT NULL, " +
                KEY_DESCRIPTION + " TEXT NOT NULL, " +
                KEY_STATUS + " TEXT NOT NULL, " +
                KEY_USER_ID + " TEXT NOT NULL, " +
                KEY_BOOKING_DATE + " TEXT NOT NULL, " +
                KEY_YEARS + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APARTMENTS);
        // Recreate the tables
        onCreate(db);
    }

    public void register(UsersModel users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.getUserName());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_PHONE, users.getPhone());
        values.put(KEY_PASSWORD, users.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public List<UsersModel> getAllUsers() {
        List<UsersModel> usersList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UsersModel users = new UsersModel();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setUserName(cursor.getString(1));
                users.setEmail(cursor.getString(2));
                users.setPhone(cursor.getString(3));
                users.setPassword(cursor.getString(4));

                usersList.add(users);
            } while (cursor.moveToNext());
        }

        return usersList;
    }

    // Method to add an apartment
    public void addApartment(ApartmentModel apartment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, apartment.getImage());
        values.put(KEY_LOCATION, apartment.getLocation());
        values.put(KEY_PRICE, apartment.getPrice());
        values.put(KEY_OWNER_NAME, apartment.getOwnerName());
        values.put(KEY_OWNER_PHONE, apartment.getOwnerPhone());
        values.put(KEY_DESCRIPTION, apartment.getDescription());
        values.put(KEY_STATUS, apartment.getStatus());
        values.put(KEY_USER_ID, apartment.getUserId());
        values.put(KEY_BOOKING_DATE, apartment.getBookingDate());
        values.put(KEY_YEARS, apartment.getYears());

        // Inserting Row
        db.insert(TABLE_APARTMENTS, null, values);
        db.close(); // Closing database connection
    }

    // Method to get all apartments
    public List<ApartmentModel> getAllApartments() {
        List<ApartmentModel> apartmentList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_APARTMENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ApartmentModel apartment = new ApartmentModel();
                apartment.setId(Integer.parseInt(cursor.getString(0)));
                apartment.setImage(cursor.getString(1));
                apartment.setLocation(cursor.getString(2));
                apartment.setPrice(cursor.getString(3));
                apartment.setOwnerName(cursor.getString(4));
                apartment.setOwnerPhone(cursor.getString(5));
                apartment.setDescription(cursor.getString(6));
                apartment.setStatus(cursor.getString(7));
                apartment.setUserId(cursor.getString(8));
                apartment.setBookingDate(cursor.getString(9));
                apartment.setYears(cursor.getString(10));
                // Adding apartment to list
                apartmentList.add(apartment);
            } while (cursor.moveToNext());
        }

        // return apartment list
        return apartmentList;
    }

    // Method to get apartments by user ID
    public List<ApartmentModel> getApartmentsByUserId(String userId) {
        List<ApartmentModel> apartmentList = new ArrayList<>();
        // Select Query
        String selectQuery = "SELECT  * FROM " + TABLE_APARTMENTS + " WHERE " + KEY_USER_ID + " = '" + userId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ApartmentModel apartment = new ApartmentModel();
                apartment.setId(Integer.parseInt(cursor.getString(0)));
                apartment.setImage(cursor.getString(1));
                apartment.setLocation(cursor.getString(2));
                apartment.setPrice(cursor.getString(3));
                apartment.setOwnerName(cursor.getString(4));
                apartment.setOwnerPhone(cursor.getString(5));
                apartment.setDescription(cursor.getString(6));
                apartment.setStatus(cursor.getString(7));
                apartment.setUserId(cursor.getString(8));
                apartment.setBookingDate(cursor.getString(9));
                apartment.setYears(cursor.getString(10));
                // Adding apartment to list
                apartmentList.add(apartment);
            } while (cursor.moveToNext());
        }

        // return apartment list
        return apartmentList;
    }

    // Method to update an apartment
    public int updateApartment(ApartmentModel apartment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, apartment.getImage());
        values.put(KEY_LOCATION, apartment.getLocation());
        values.put(KEY_PRICE, apartment.getPrice());
        values.put(KEY_OWNER_NAME, apartment.getOwnerName());
        values.put(KEY_OWNER_PHONE, apartment.getOwnerPhone());
        values.put(KEY_DESCRIPTION, apartment.getDescription());
        values.put(KEY_STATUS, apartment.getStatus());
        values.put(KEY_USER_ID, apartment.getUserId());
        values.put(KEY_BOOKING_DATE, apartment.getBookingDate());
        values.put(KEY_YEARS, apartment.getYears());

        // Updating row
        return db.update(TABLE_APARTMENTS, values, KEY_ID_APARTMENT + " = ?",
                new String[]{String.valueOf(apartment.getId())});
    }


    public void deleteApartment(int apartmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APARTMENTS, KEY_ID_APARTMENT + " = ?", new String[]{String.valueOf(apartmentId)});
        db.close();
    }

    public UsersModel getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID_USER, KEY_NAME, KEY_EMAIL, KEY_PHONE, KEY_PASSWORD},
                KEY_ID_USER + "=?", new String[]{String.valueOf(userId)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UsersModel user = new UsersModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return user;
    }

}