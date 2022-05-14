package com.example.ballot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    String EmailHolder;
    String NameHolder;
    TextView Email;
    TextView UserName;
    Button LogOUT ;
    Button sett;
    TextView textOnToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // for toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        textOnToolBar = (TextView) findViewById(R.id.SetTitle_main);
        textOnToolBar.setText("");


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,homeOrginal.class);
                startActivity(intent);
            }
        });

        Email = (TextView)findViewById(R.id.changeEmail);
        UserName = (TextView)findViewById(R.id.changeName);
        LogOUT = (Button)findViewById(R.id.btn_logout);
        sett = findViewById(R.id.settSilent);
        // Receiving User Email Send By MainActivity.
        //EmailHolder = intent.getStringExtra("userInfo");

        // Setting up received email to TextView.
        UserName.setText("Name:" +" "+ Login.name1);
        Email.setText("Email:"  +" "+ Login.email1);


        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,Login.class);
                startActivity(intent);
                //Finishing current DashBoard activity on button click.
                finish();

                Toast.makeText(Home.this,"Log Out Successfully", Toast.LENGTH_LONG).show();

            }
        });

        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,SilentMode.class);
                startActivity(intent);

            }
        });

    }
}

//public class Home extends AppCompatActivity {
//    TextView text;
//
//    @Override
//    public void onBackPressed() {
//        Home.this.finish();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        text = findViewById(R.id.changeText);
//        Intent intent = getIntent();
//        String s2 = intent.getStringExtra("email");
//        text.setText(s2);
//
//
//    }
//}