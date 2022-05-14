package com.example.ballot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ballot.Sql.DBHelper;

public class Home extends AppCompatActivity {
    TextView text;
    Button postPollBtn;
    Button viewPollBtn;
    @Override
    public void onBackPressed() {
        Home.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postPollBtn = findViewById(R.id.goPostPoll);
        viewPollBtn = findViewById(R.id.goViewPolls);
       // text = findViewById(R.id.changeText);
//        Intent intent = getIntent();
//        String s2 = intent.getStringExtra("email");
//        text.setText(s2);
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);
        dbHelper.insertPoll("T1","Q1","37.4219983","-122.084");
        dbHelper.insertPoll("T2","Q2","37.4219983","-122.084");
        dbHelper.insertPoll("T3","Q13","37.4219983","-122.084");
        // onClick post poll button
        viewPollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // go to post a poll screen (make sure it work)
                    Intent intent = new Intent(Home.this,VotePoll.class);
                    startActivity(intent);

            }
        });


    }
}