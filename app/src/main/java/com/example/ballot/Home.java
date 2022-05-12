package com.example.ballot;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Email = (TextView)findViewById(R.id.changeEmail);
        UserName = (TextView)findViewById(R.id.changeName);
        LogOUT = (Button)findViewById(R.id.btn_logout);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        NameHolder = extras.getString("EXTRA_USERNAME");
        EmailHolder = extras.getString("EXTRA_Email");

        // Receiving User Email Send By MainActivity.
        //EmailHolder = intent.getStringExtra("userInfo");

        // Setting up received email to TextView.
        UserName.setText("Name:" +" "+ NameHolder);
        Email.setText("Email:"  +" "+ EmailHolder);


        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.
                finish();

                Toast.makeText(Home.this,"Log Out Successfully", Toast.LENGTH_LONG).show();

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