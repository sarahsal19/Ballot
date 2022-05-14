package com.example.ballot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ballot.Sql.DBHelper;

import java.util.Random;


public class VotePoll extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private NotificationManagerCompat notificationManager;
    private NotificationManager manager;
    public static final String ch1 = "channel1";
    String pollT;
    String pollQ;
    int totalY = 0;
    int totalN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_poll);

        DBHelper db;
        db = new DBHelper(this);

        TextView title= findViewById(R.id.poll_title);
        TextView ques= findViewById(R.id.poll_question);
        Button yesBtn= findViewById(R.id.yes);
        Button noBtn= findViewById(R.id.no);

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = (NotificationManager) getSystemService(NotificationManager.class);
        }

        // get intent content
        pollT= getIntent().getStringExtra("title");
        pollQ= getIntent().getStringExtra("message");

        // display in the page
        title.setText(pollT);
        ques.setText(pollQ);
        total();
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update poll "yes"
                Cursor cursor = db.readPollData();
                if(cursor.getCount() == 0){
                    Log.d("tagVoteY", "No data");
                }else{
                    while (cursor.moveToNext()){
                        if(pollT.equals(cursor.getString(1)) ) {
                            db.updateVotNum(pollT,1,(cursor.getInt(5))+1);
                            totalY = (cursor.getInt(5))+1;
                        }
                    }
                }
                sendHighNoti(pollT,pollQ,totalY,totalN);
                // go to home screen
                Intent intent = new Intent(VotePoll.this,Home.class);
                startActivity(intent);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update poll "no"
                Cursor cursor = db.readPollData();
                if(cursor.getCount() == 0){
                    Log.d("tagVoteN", "No data");
                }else{
                    while (cursor.moveToNext()){
                        if(pollT.equals(cursor.getString(1)) ) {
                            db.updateVotNum(pollT,0,(cursor.getInt(6))+1);
                            totalN = (cursor.getInt(6))+1;
                        }
                    }
                }
                sendHighNoti(pollT,pollQ,totalY,totalN);
                // go to home screen
                Intent intent = new Intent(VotePoll.this,Home.class);
                startActivity(intent);
            }
        });
    }
public void total (){
    DBHelper db;
    db = new DBHelper(this);

    Cursor cursor = db.readPollData();
    if(cursor.getCount() == 0){
        Log.d("tagVoteY", "No data");
    }else{
        while (cursor.moveToNext()){
            if(pollT.equals(cursor.getString(1)) ) {
                totalY = (cursor.getInt(5));
                totalN = (cursor.getInt(6));

            }
        }
    }
}
    public void sendHighNoti(String titleP, String quesP, int totalY, int totalN) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = manager.getNotificationChannel(ch1);
            if (channel1 == null) {
                channel1 = new NotificationChannel(
                        ch1,
                        "Channel 1",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel1.setDescription("This is Channel 1");
                manager.createNotificationChannel(channel1);
            }
        }

        int id= new Random().nextInt();

        Intent intent= new Intent(VotePoll.this, Result.class);
        intent.putExtra("title",titleP);
        intent.putExtra("ques",quesP);
        intent.putExtra("yesT",totalY);
        intent.putExtra("noT",totalN);
        pendingIntent= PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(VotePoll.this, ch1)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Voting Result")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(id, builder.build());
    }
}
