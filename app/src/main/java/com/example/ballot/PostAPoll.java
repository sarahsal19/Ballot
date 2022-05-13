package com.example.ballot;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ballot.Sql.DBHelper;

public class PostAPoll extends AppCompatActivity //implements LocationListener
{
    EditText title , question;
    Button submitBtn;
    DBHelper dbHelper;
   // protected LocationManager locationManager;
   // protected LocationListener locationListener;
    protected Context context;
    String provider;
    String latitude,longitude;
    BroadcastReceiver broadcastReceiver;
    String cooordinates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_poll);

        title=findViewById(R.id.poll_title);
        question=findViewById(R.id.poll_question);
        submitBtn = findViewById(R.id.poll_submit);
        // make sure of the helper
        dbHelper = new DBHelper(this);
        // reference so that I can get the location

          //  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

         // added for location /////////////////////////////////////////////////////

 if(!runtime_permissions())
enable_button();




        // added for location /////////////////////////////////////////////////////

         // for validation:
        // check if the 2 fields are empty
        // if not: save the title, question and the user location to the Polls table in DB and show toast
        // then set title and question to ""
// When you need the permission, e.g. onCreate, OnClick etc.

        // Add back button and toolbar

        // onclick submit a poll button
        /*
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleCheck = title.getText().toString();
                String questionCheck = question.getText().toString();

                // check fields
                if (isEmpty(title)){
                    title.setError("Title can not be empty");
                }

                if (isEmpty(question)){
                    question.setError("Question can not be empty");
                }

                // get user current location (method onLocationChanged)

                // Add the new poll to polls table (trim them)
                // id (PK) | title | question | latitude | longitude
                System.out.println("lat is "+ latitude+ " long is "+longitude);
                boolean result =dbHelper.insertPoll(titleCheck,questionCheck,latitude,longitude);
                if (result){
                    showAToast("Poll was submitted successfully");

                    // go to home screen
                    Intent intent = new Intent(PostAPoll.this,Home.class);
                    title.setText("");
                    question.setText("");
                    startActivity(intent);
                }else {
                    showAToast("Something went wrong, poll was not submitted ");
                }

                dbHelper.close();
            }
        });
        */

    }



    // for location
    @Override
    protected void onResume(){
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    cooordinates = intent.getStringExtra("coordinates");
                    latitude = intent.getStringExtra("lat");
                    longitude = intent.getStringExtra("lng");
                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("swe483.action.GPS_LOCATION"));
    }

    public void enable_button(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleCheck = title.getText().toString();
                String questionCheck = question.getText().toString();

                // check fields
                if (isEmpty(title)){
                    title.setError("Title can not be empty");
                }

                if (isEmpty(question)){
                    question.setError("Question can not be empty");
                }

                // get user current location (method onLocationChanged)

                // Add the new poll to polls table (trim them)
                // id (PK) | title | question | latitude | longitude
                System.out.println("lat is "+ latitude+ " long is "+longitude);
                boolean result =dbHelper.insertPoll(titleCheck,questionCheck,latitude,longitude);
                if (result){
                    showAToast("Poll was submitted successfully");

                    // go to home screen
                    Intent intent = new Intent(PostAPoll.this,Home.class);
                    title.setText("");
                    question.setText("");
                    startActivity(intent);
                }else {
                    showAToast("Something went wrong, poll was not submitted ");
                }

                dbHelper.close();
            }
        });

    }

    public boolean runtime_permissions(){
        if(Build.VERSION.SDK_INT >=23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String [] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
            return true;
        }
        // if din't need runtime permission
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
   if (requestCode == 100){
       if(grantResult[0] == PackageManager.PERMISSION_GRANTED && grantResult[1] == PackageManager.PERMISSION_GRANTED){
           enable_button();
       }else{
           runtime_permissions();
       }
   }
    }



/*
    // set lat and long for location
    @Override
    public void onLocationChanged(Location location) {

        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }
*/
    // show a toast
    public void showAToast(String text){
        Toast.makeText(PostAPoll.this,text,Toast.LENGTH_LONG).show();
    }


    // check if text field is empty
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

}
