package com.example.ballot.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase DB = this.getWritableDatabase();

    public DBHelper(Context context ) {
        super(context,"UserData",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create Table UserDetails(userID TEXT primary key,name TEXT,password PASSWORD)");

        // create polls table
        DB.execSQL("create Table Polls(pollID INTEGER primary key AUTOINCREMENT, title TEXT,question TEXT, latitude TEXT, longitude TEXT, yes INTEGER, noo INTEGER)");

        //create votedpolls table
        DB.execSQL("CREATE TABLE VotedPolls (vName TEXT, pollid INTEGER, yes INTEGER, noo INTEGER, vTitle TEXT, FOREIGN KEY (vTitle) REFERENCES Polls(title), FOREIGN KEY (vName) REFERENCES UserDetails(name), FOREIGN KEY (pollid) REFERENCES Polls(pollID), PRIMARY KEY (vUsername, pollid))");

//        DB.execSQL("CREATE TABLE poll (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, des TEXT, Location TEXT, status BOOLEAN, YES INTEGER, NOO INTEGER, Usernamee TEXT, FOREIGN KEY (Usernamee) REFERENCES Users(Username)) ");

    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");

        DB.execSQL("drop Table if exists Polls");

        DB.execSQL("drop Table if exists VotedPolls");
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

    public Cursor readPollData(){

    String query = "SELECT * FROM Polls";
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = null;
    if(db != null){
        cursor = db.rawQuery(query, null);
    }
    return cursor;
}
    public Boolean updateVotNum(String title, int i, int total){

    SQLiteDatabase DB= this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    if(i == 1){
        contentValues.put("yes",total);
    }
    if(i == 0){
        contentValues.put("noo",total);
    }

    Cursor cursor=DB.rawQuery("SELECT * FROM Polls where title=?", new String[]{title});

    if(cursor.getCount()>0){
        long result=DB.update("Polls", contentValues,"title= ?", new String[]{title});

        if(result==-1){
            return false;
        }

        return true;}

    return false;
}
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Polls");
    }

}
