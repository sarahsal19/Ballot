package com.example.ballot;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import com.example.ballot.R;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.media.AudioManager;
public class SilentMode extends AppCompatActivity {

    TextView textOnToolBar;
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silent_mode);

        // for toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        textOnToolBar = (TextView) findViewById(R.id.SetTitle_main);
        textOnToolBar.setText("");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SilentMode.this,Home.class);
                startActivity(intent);
            }
        });
        Button silentBtn=(Button) findViewById(R.id.silent_Mode2) ;
        Button NormalBtn=(Button) findViewById(R.id.normalMode2) ;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
//            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//            startActivity(intent);
//        }


        silentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(SilentMode.this, "Silent Mode is set", Toast.LENGTH_SHORT).show();
            }
        });

        NormalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(SilentMode.this, "Normal Mode is set", Toast.LENGTH_SHORT).show();
            }
        });


    }
}