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
import android.location.Address;
import android.location.Geocoder;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.ballot.Sql.DBHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;



public class PostAPoll extends LocationBaseActivity//AppCompatActivity //implements LocationListener
{
    EditText title , question;
    TextView textOnToolBar;
    Button submitBtn;
    DBHelper db;
   // protected LocationManager locationManager;
   // protected LocationListener locationListener;
    protected Context context;
    String provider;
    String latitude,longitude;
    BroadcastReceiver broadcastReceiver;
    String cooordinates;
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_poll);

        title=findViewById(R.id.poll_title);
        question=findViewById(R.id.poll_question);
        submitBtn = findViewById(R.id.poll_submit);
        // make sure of the helper
        db = new DBHelper(this);
        // reference so that I can get the location

          //  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

         // added for location /////////////////////////////////////////////////////

 if(!runtime_permissions())
enable_button();





    }*/



    // for location
    @Override
    protected void onResume(){
        super.onResume();
        /*
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    cooordinates = intent.getStringExtra("coordinates");
                    latitude = intent.getStringExtra("latitude");
                    longitude = intent.getStringExtra("longitude");

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("swe483.action.GPS_LOCATION"));
        */

        if (getLocationManager().isWaitingForLocation() && !getLocationManager().isAnyDialogShowing()){
            Log.d("display","display progress");
        }
        else{
            initLocation();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_poll);

        // for toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        textOnToolBar = (TextView) findViewById(R.id.SetTitle_main);
        textOnToolBar.setText("");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostAPoll.this,Home.class);
                title.setText("");
                question.setText("");
                startActivity(intent);
            }
        });



        title=findViewById(R.id.poll_title);
        question=findViewById(R.id.poll_question);
        submitBtn = findViewById(R.id.poll_submit);


        db = new DBHelper(this);
        // delete records
       // db.deletePollsRecords();

        initLocation();
        enable_button();

/*
        if(!runtime_permissions())
            enable_button();
*/

    }

    public void enable_button(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Runtime permissions
                /*
                if (ContextCompat.checkSelfPermission(PostAPoll.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    androidx.core.app.ActivityCompat.requestPermissions(PostAPoll.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },100);
                }
*/

                // again
                title = findViewById(R.id.poll_title);
                question = findViewById(R.id.poll_question);

                // check fields
                if (isEmpty(title)){
                    title.setError("Title can not be empty");
                   // showAToast("Title can not be empty");
                    return;
                }

                if (isEmpty(question)){
                   // showAToast("Question can not be empty");
                    question.setError("Question can not be empty");
                    return;
                }

                // get user current location (method onLocationChanged)

                // Add the new poll to polls table (trim them)
                // id (PK) | title | question | latitude | longitude | yes | noo
                System.out.println("lat is "+ latitude+ " long is "+longitude);
                if (latitude == null){
                    showAToast("lat is null");
                    return;
                }

                boolean result =db.insertPoll(title.getText().toString(),question.getText().toString(),latitude,longitude);
                if (result){
                    showAToast("Poll was submitted successfully");
                    printTable();
                    // go to home screen
                    Intent intent = new Intent(PostAPoll.this,Home.class);
                    title.setText("");
                    question.setText("");
                    startActivity(intent);

                }else {
                    showAToast("Something went wrong, poll was not submitted ");
                }

               // db.close();
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


    public void printTable() {
        String tableString = "";

        Cursor cursor = db.getLastPollDataCheck();
String [] columns = {"pollID", "title","question", "latitude", "longitude"};
int idpoll = cursor.getColumnIndex("pollID");
int titlePoll = cursor.getColumnIndex("title");
int qPoll = cursor.getColumnIndex("question");
int latiPoll = cursor.getColumnIndex("latitude");
int longiPoll = cursor.getColumnIndex("longitude");
int yesPoll = cursor.getColumnIndex("yes");
int nooPoll = cursor.getColumnIndex("noo");
Log.d("yes is",String.valueOf(yesPoll));
Log.d("question is",String.valueOf(qPoll));
        while (cursor.moveToNext()){
            columns[0] = Integer.toString((cursor.getInt(idpoll)));
            columns[1] = cursor.getString(titlePoll);
            columns[2] = cursor.getString(qPoll);
            columns[3] = cursor.getString(latiPoll);
            columns[4] = cursor.getString(longiPoll);
         //   columns[5] = Integer.toString((cursor.getInt(yesPoll)));
          //  columns[6] = Integer.toString((cursor.getInt(nooPoll)));

            tableString += ("\n" +columns[0]+ " "
                                 +columns[1]+ " "
                                 +columns[2]+ " "
                                 +columns[3]+ " "
                                 +columns[4]);//+ " "
                              //   +columns[5]+ " "
                               //   +columns[6]);
        }
        Log.d("printTable", tableString);
       //  System.out.println(tableString);
        db.close();
    }

    private void initLocation(){
        getLocation();
}
    @Override
    public void onLocationChanged(Location location) {

            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());

            Log.d("lat is",latitude);

    }

    @Override
    public void onLocationFailed(int type) {

    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Location permission!", "Would you mind to turn GPS on?");
    }


}
