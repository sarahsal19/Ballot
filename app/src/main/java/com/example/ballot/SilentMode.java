package com.example.ballot;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import com.example.ballot.R;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.media.AudioManager;
class Silent_Mode extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silent_mode);

        Button silentBtn=(Button) findViewById(R.id.SilentMode) ;
        Button NormalBtn=(Button) findViewById(R.id.NormalMode) ;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }


        silentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(Silent_Mode.this, "Silent Mode is set", Toast.LENGTH_SHORT).show();
            }
        });

        NormalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(Silent_Mode.this, "Normal Mode is set", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
