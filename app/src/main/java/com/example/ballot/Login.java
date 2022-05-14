package com.example.ballot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ballot.Sql.DBHelper;
public class Login extends AppCompatActivity {
    public static String name1;
    public static String email1;

    EditText email , password;
    String nameFromDB;
    Button btnSubmit;
    TextView createAcc;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean e=false,p=false;
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.text_email);
        password=findViewById(R.id.text_pass);
        btnSubmit = findViewById(R.id.btnSubmit_login);
        dbHelper = new DBHelper(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailCheck = email.getText().toString();
                String passCheck = password.getText().toString();
                Cursor  cursor = dbHelper.getData();
                if(cursor.getCount() == 0){
                    Toast.makeText(Login.this,"No entries Exists",Toast.LENGTH_LONG).show();
                }
                if (loginCheck(cursor,emailCheck,passCheck)) {
                    nameFromDB = cursor.getString(1);
                    //Intent intent = new Intent(Login.this,Home.class);
//                    Bundle extras = new Bundle();
//                    extras.putString("EXTRA_USERNAME",nameFromDB);
//                    extras.putString("EXTRA_Email",emailCheck);
                   // intent.putExtras(extras);
                   // intent.putExtra("userInfo",new String[] { emailCheck, nameFromDB});
                    email.setText("");
                    password.setText("");
                    name1= nameFromDB;
                    email1 = emailCheck;
                    // by Modhi
                    // got to home orginal
                    Intent intent = new Intent(Login.this,homeOrginal.class);
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_USERNAME",nameFromDB);
                    extras.putString("EXTRA_Email",emailCheck);
                    intent.putExtras(extras);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setCancelable(true);
                    builder.setTitle("Wrong Credential");
                    builder.setMessage("Make sure to write your correct email and password ");
                    builder.show();
                }
                dbHelper.close();
            }
        });
        createAcc=findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
    }
    public static boolean loginCheck(Cursor cursor,String emailCheck,String passCheck) {
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(emailCheck)) {
                if (cursor.getString(2).equals(passCheck)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
