package com.example.ballot.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
   // SQLiteDatabase DB = this.getWritableDatabase();

    public DBHelper(Context context ) {
        super(context,"UserData",null, 2);

    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(userID TEXT primary key,name TEXT,password PASSWORD)");

        // create polls table
        DB.execSQL("create Table Polls(pollID INTEGER primary key AUTOINCREMENT, title TEXT,question TEXT, latitude TEXT, longitude TEXT, yes INTEGER, noo INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");

        DB.execSQL("drop Table if exists Polls");
    }

    public Boolean insetUserData(String name,String email,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        //contentValues.put("number",number);


        long result= DB.insert("UserDetails",null,contentValues);
        if (result == -1){
            return false;
        }else {
           // DB.execSQL("DELETE FROM " + "UserDetails" + " WHERE "+"userID"+"='"+KEY_NAME+"'" );
            DB.close();
            return true;
        }
    }

    public Boolean insertPoll(String title,String question,String latitude, String longitude){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("question",question);
        contentValues.put("latitude",latitude);
        contentValues.put("longitude",longitude);
        contentValues.put("yes",0);
        contentValues.put("noo",0);
        long result= DB.insert("Polls",null,contentValues);
        if (result == -1){
            return false;
        }else {
            DB.close();
            return true;
        }
    }


    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails ",null);
        return cursor;
    }

    public Cursor getLastPollDataCheck(){
        SQLiteDatabase DB = this.getReadableDatabase();

        String [] columns = {"pollID", "title","question", "latitude", "longitude"};

        Cursor cursor = DB.query("Polls", columns,null,null,null,null, "pollID");
        return cursor;
    }

    public void deletePollsRecords(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from Polls");
    }

}
