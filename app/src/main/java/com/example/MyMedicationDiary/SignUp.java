package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private TextView log;
    private TextView next;
    public EditText user;
    public EditText password;
    public EditText passwordagain;

    public EditText email;
    public EditText phone;
    public TextView sth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        log=(TextView)findViewById(R.id.logiin);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });

        next=(TextView)findViewById(R.id.sup);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo2();
            }
        });

        user=(EditText)findViewById(R.id.usrusr);
        password=(EditText)findViewById(R.id.passwrd);
        passwordagain=(EditText)findViewById(R.id.passwrdagain);

        email=(EditText)findViewById(R.id.mail);
        phone=(EditText)findViewById(R.id.mobphone);




    }

    private void goTo3() {
        Intent intent=new Intent(SignUp.this, contSignUp.class);

        String username = user.getText().toString();
        String pass = password.getText().toString();
        String Email = email.getText().toString();
        String mobile = phone.getText().toString();

        intent.putExtra("user",username);
        intent.putExtra("pass",pass);
        intent.putExtra("email",Email);
        intent.putExtra("mobile",mobile);


        startActivity(intent);
    }

    private void goTo(){
        Intent intent=new Intent(SignUp.this, SecondActivity.class);
        startActivity(intent);


    }
    private void goTo2(){

        String username = user.getText().toString();
        String pass = password.getText().toString();
        String Email = email.getText().toString();
        String mobile = phone.getText().toString();
        String passagain = passwordagain.getText().toString();
        if(pass.equals(passagain)) {

            String method = "signup";
            String type = "1";
            background bg = new background(this);
            bg.execute(method, type, username, pass, Email, mobile);
        }
        else{
            Toast.makeText(getApplicationContext(),"يرجى التأكد من تطابق كلمتي المرور",Toast.LENGTH_LONG).show();
        }

    }
}
