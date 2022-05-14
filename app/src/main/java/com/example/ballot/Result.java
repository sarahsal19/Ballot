package com.example.ballot;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        TextView title= findViewById(R.id.titleR);
        TextView ques= findViewById(R.id.quesR);
        TextView y= findViewById(R.id.yesR);
        TextView n= findViewById(R.id.noR);


        // get intent content
        String titleP= getIntent().getStringExtra("title");
        String quesP= getIntent().getStringExtra("ques");
        int yP = getIntent().getIntExtra("yesT", 0);
        int nP = getIntent().getIntExtra("noT", 0);
        // display in the page
        title.setText(titleP);
        ques.setText(quesP);
        y.setText(yP);
        n.setText(nP);
    }
}