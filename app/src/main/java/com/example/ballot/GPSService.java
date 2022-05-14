package com.example.ballot;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GPSService extends Service {
    Thread triggerService;
    LocationManager locationManager;
    LocationListener myLocationListener;
    String GPS_Filter = "swe483.action.GPS_LOCATION";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (locationManager != null){
            locationManager.removeUpdates(myLocationListener);
        }

    }
    @Override

    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);
        triggerService = new Thread( new Runnable() {
            // @Override
            public void run() {
                try {
                    Looper.prepare();
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    myLocationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            String lat = String.valueOf(location.getLatitude());
                            String lng = String.valueOf(location.getLongitude());

                            Intent myFilter = new Intent(GPS_Filter);
                            myFilter.putExtra("coordinates", lat + " " + lng);
                            myFilter.putExtra("latitude", lat);
                            myFilter.putExtra("longitude", lng);
                            sendBroadcast(myFilter);
                        }
                    };
                    long minTime = 3000;
                    float minDist = 0;
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDist, myLocationListener);
                    Looper.loop();
                }
                catch(Exception e){
                    Log.e("MAPS", e.getMessage());
                }
            }
        });
        triggerService.start();
        return flags;
    }



}
