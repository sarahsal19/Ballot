package com.example.ballot;

import android.app.ProgressDialog;
//import android.location.Address;
//import android.location.Geocoder;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

import com.example.ballot.Sql.DBHelper;
import java.util.ArrayList;

public class ViewPoll extends LocationBaseActivity {

    private ProgressDialog progressDialog;

    private RecyclerView pollsList;
    private ViewPollAdapter adapter;
    ArrayList<String> pollTitle, pollQues;
    DBHelper db;
    String latitude,longitude;
    TextView textOnToolBar ;
String NameHolder, EmailHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_poll_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        textOnToolBar = (TextView) findViewById(R.id.SetTitle_main);
        textOnToolBar.setText("");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPoll.this,homeOrginal.class);
                startActivity(intent);
            }
        });

        db = new DBHelper(this);
        pollTitle = new ArrayList<>();
        pollQues = new ArrayList<>();

        //latitude = "37.4219983";
       // longitude = "-122.084";
        //fineNearestLocation();
        initLocation();


//        adapter = new ViewPollAdapter(ViewPoll.this, this , pollTitle, pollQues);
//
//        Log.d("----- title",pollTitle.get(0));
//        Log.d("----- ques",pollQues.get(0));
//        pollsList = (RecyclerView) findViewById(R.id.pollList);
//        pollsList.setAdapter(adapter);
       // pollsList.setLayoutManager(new LinearLayoutManager(ViewPoll.this));
    }

    private void fineNearestLocation(){
        Log.d("--- location", "in fineNearestLocation");
        Cursor cursor = db.readPollData();
        int i = 0;
        if(cursor.getCount() == 0){
            Log.d("tag", "No data");
        }else{
            while (cursor.moveToNext()){
                if(latitude.equals(cursor.getString(3)) || latitude.contains(cursor.getString(3)) || longitude.equals(cursor.getString(4)) || longitude.contains(cursor.getString(4)) ) {

              if(pollTitle.isEmpty()){
                  pollTitle.add(cursor.getString(1));
                  pollQues.add(cursor.getString(2));
           }
              else if(!pollTitle.contains(cursor.getString(1))){
                  pollTitle.add(cursor.getString(1));
                  pollQues.add(cursor.getString(2));
                  i= i+1;
              }

                }
            }
            }
        printTable() ;
    }
    public void printTable() {
        String tableString = "";

        Cursor cursor = db.readPollData();
        String [] columns = {"pollID", "title","question", "latitude", "longitude", "yes", "noo"};
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
            columns[5] = Integer.toString((cursor.getInt(yesPoll)));
            columns[6] = Integer.toString((cursor.getInt(nooPoll)));

            tableString += ("\n" +columns[0]+ " "
                    +columns[1]+ " "
                    +columns[2]+ " "
                    +columns[3]+ " "
                    +columns[4]+ " "
                    +columns[5]+ " "
                    +columns[6]);
        }
        Log.d("printTable", tableString);
        //  System.out.println(tableString);
        cursor.close();
        db.close();
    }
    private void initLocation(){
        getLocation();
    }


    @Override
    public void onLocationChanged(Location location) {

        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());

        Log.d("--- latitude", latitude);
        Log.d("--- longitude", longitude);

//        try {
//            Geocoder geocoder = new Geocoder(NearPollsActivity.this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//            String address = addresses.get(0).getAddressLine(0);
//            addresss =address;
//
//            Log.i(Global.TAG,"address : "+ address);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        fineNearestLocation();

        adapter = new ViewPollAdapter(ViewPoll.this, this , pollTitle, pollQues);

        Log.d("----- title",pollTitle.get(0));
        Log.d("----- ques",pollQues.get(0));

        pollsList = (RecyclerView) findViewById(R.id.pollList);
        pollsList.setAdapter(adapter);

        dismissProgress();
    }

    @Override
    public void onLocationFailed(int type) {
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Location permission!", "Would you want to turn GPS on?");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
        else {
            initLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting your location");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}