package com.example.ballot.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance = null;// Sara
    private static Context context;// Sara

    public DBHelper(Context context ) {
        super(context,"UserData",null, 1);
    }
    //Sara
    public static DBHelper getInstance(){
        if (instance == null){
            instance = new DBHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(userID TEXT primary key,name TEXT,password PASSWORD)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");
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
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails ",null);
        return cursor;
    }
    // Sara
    public Cursor readAllData() {


        // String query = "SELECT * FROM poll where Usernamee=?\",new String[]{UserName}";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){

            cursor=  db.rawQuery("Select * from poll ", null);

        }
        return cursor;
    }
    public Cursor readAllVotes2(String idd) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from pollvoter where pollid = ? ", new String[]{idd});


        if(db != null){
            return cursor ;
        }
        return cursor;
    }
}
