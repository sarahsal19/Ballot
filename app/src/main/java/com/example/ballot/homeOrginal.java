package com.example.ballot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class homeOrginal extends AppCompatActivity {
    TextView text;
    Button postPollBtn;
    @Override
    public void onBackPressed() {
        homeOrginal.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // for toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_login_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeOrginal.this,Login.class);
                startActivity(intent);
                finish();
            }
        });



        postPollBtn = findViewById(R.id.goPostPoll);
        // text = findViewById(R.id.changeText);
        // Intent intent = getIntent();
        // String s2 = intent.getStringExtra("email");
        // text.setText(s2)

        // onClick post poll button
        postPollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to post a poll screen (make sure it work)
                Intent intent = new Intent(homeOrginal.this,PostAPoll.class);
                startActivity(intent);

            }
        });

    }
}