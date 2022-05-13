package com.example.ballot;

import android.app.ProgressDialog;
//import android.location.Address;
//import android.location.Geocoder;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.Window;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_poll_list);

       db = new DBHelper(this);
        pollTitle = new ArrayList<>();
        pollQues = new ArrayList<>();

        initLocation();

        adapter = new ViewPollAdapter(ViewPoll.this, this , pollTitle, pollQues);
        pollsList = (RecyclerView) findViewById(R.id.pollList);
        pollsList.setAdapter(adapter);

        //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void fineNearestLocation(){
        Cursor cursor = db.readPollData();
        if(cursor.getCount() == 0){
            Log.d("tag", "No data");
        }else{
            while (cursor.moveToNext()){
                if(latitude.equals(cursor.getString(3)) || latitude.contains(cursor.getString(3)) || longitude.equals(cursor.getString(4)) || longitude.contains(cursor.getString(4)) ) {
                    pollTitle.add(cursor.getString(1));
                    pollQues.add(cursor.getString(2));
                }
            }
        }
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


