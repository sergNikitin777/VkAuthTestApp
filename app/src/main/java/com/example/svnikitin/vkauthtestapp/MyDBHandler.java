package com.example.svnikitin.vkauthtestapp;

/**
 * Created by svnikitin on 07.02.2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "friends.db";
    public static final String TABLE_FRIENDS = "friends";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_FRIENDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT " +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(db);
    }

    //Añade un nuevo Row a la Base de Datos

    public void addFriend(Friend friend) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, friend.getFirstName());
        values.put(COLUMN_LAST_NAME, friend.getLastName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FRIENDS, null, values);
        db.close();

    }

    public void updateFriend(Friend friend){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, friend.getFirstName());
        values.put(COLUMN_LAST_NAME, friend.getLastName());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_FRIENDS, values, COLUMN_ID + "= ?", new String[] { String.valueOf(friend.getId())});
        db.close();

    }

    // Borrar una persona de la Base de Datos

    public void removeFriend(int persona_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FRIENDS + " WHERE " + COLUMN_ID + " = " + persona_id + ";");
        db.close();
    }

    //listar por id

    public Cursor getFriendById(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " + COLUMN_ID + " = " + id + ";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    //listar a todas las personas
    public Cursor getFriendList(){
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLE_FRIENDS + " WHERE 1 ORDER BY " + COLUMN_LAST_NAME + ";");
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

            return c;
        }

}




