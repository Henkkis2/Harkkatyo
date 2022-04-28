package com.example.harkkatyo;
// This database is build from watching this video. https://www.youtube.com/watch?v=8obgNNlj3Eo
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Tietokanta extends SQLiteOpenHelper {
    public static final String DBNAME = "allUserInfo.db";


    public Tietokanta(Context context) {
        super(context, "allUserInfo.db", null, 1);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table list(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists list");
    }

    public boolean checkNameOnly (String userName) { // Checks if user already exist in database, when creating new userAccount.
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select * from list where username = ?", new String[]{userName});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkBoth (String userName, String passWord) { // When login checks if username and password are true and approves login.
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select * from list where username = ? and password = ?", new String[] {userName,passWord});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean createUser (String userName, String passWord) { // Create useraccount to the database, after if there is not already user.
        SQLiteDatabase dataBase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", userName);
        cv.put("password", passWord);
        long lisays = dataBase.insert("List", null, cv);
        if (lisays == -1) {
            return false;
        } else {
            return true;
        }
    }
}
