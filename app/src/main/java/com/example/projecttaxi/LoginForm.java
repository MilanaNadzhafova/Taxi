package com.example.projecttaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        TextView txtLogin = (TextView)findViewById(R.id.txtLogin);
        TextView txtPassword = (TextView)findViewById(R.id.txtPassword);
        TextView txtCreate = (TextView)findViewById(R.id.txtCreate);
        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this,RegistrationForm      .class);
                startActivity(intent);
            }
        });
        Button btnSign = (Button) findViewById(R.id.btnLogin);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String Password, Login;
             Login =  txtLogin.getText().toString();
             Password =txtPassword.getText().toString();
             String OriginLogin = "serk";
             String OriginPassword = "123";
                if(Login.equals(OriginLogin))
                {
                    if(Password.equals(OriginPassword))
                    {
                        Intent intent = new Intent(LoginForm.this,GPSForm.class);
                        startActivity(intent);
                    }
                    else showToastPassword();
                }
                else showToastLogin();

            }


        });
    }
    public void showToastLogin() {
        
        Toast toast = Toast.makeText(getApplicationContext(),
                "Неверный логин!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public void showToastPassword() {

        Toast toast = Toast.makeText(getApplicationContext(),
                "Неверный пароль!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}