package com.example.stonks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;

    private Button btn_login;
    private TextView btn_signup;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup); // signup activity will be added if time allows

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(v);
            }
        });

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (et_password.getRight() - (et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() + et_password.getPaddingRight()))) {
                        ToggleShowPassword();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    // Toggle password et to visible
    private void ToggleShowPassword(){
        Log.d("KW", "Toggle show Password!");

        if(et_password.getTransformationMethod() == null){
            et_password.setTransformationMethod(new PasswordTransformationMethod());
        }else{
            et_password.setTransformationMethod(null);
        }
    }

    // We accept whatever for now
    private boolean ValidateLoginCredentials(){
        String _username = et_username.getText().toString();
        String _password = et_password.getText().toString();

        if(_username.matches("")){
            Toast.makeText(this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if (_password.matches("")){
            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            return true;
        }
        return false;
    }

    // If have time we'll can connect to Firebase to check for login credentials
    // Right now we'll just accept anything
    private void Login(View v){
        boolean _valid = ValidateLoginCredentials();

        if(_valid){
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
