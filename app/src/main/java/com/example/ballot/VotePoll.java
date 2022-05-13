package com.example.ballot;

import androidx.appcompat.app.AppCompatActivity;
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


public class VotePoll extends AppCompatActivity {

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

        // get intent content
        String pollT= getIntent().getStringExtra("title");
        String pollQ= getIntent().getStringExtra("message");

        // display in the page
        title.setText(pollT);
        ques.setText(pollQ);

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
                        }
                    }
                }

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
                        }
                    }
                }

                // go to home screen
                Intent intent = new Intent(VotePoll.this,Home.class);
                startActivity(intent);
            }
        });
    }
}
