package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Project's Main Class
public class MainActivity extends AppCompatActivity {
    Button loginButton, registrationButton;
    View firstView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        registrationButton = (Button) findViewById(R.id.registrationButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            onRegistration(firstView);
        }
    }

    public void onLogin (View view) {
        Log.i("Login","Clicked");
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void onRegistration (View view) {
        Log.i("Registration","Clicked");
        Intent registrationIntent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(registrationIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
