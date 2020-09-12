package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Dashboard View Class
public class ProfileActivity extends AppCompatActivity {

    private Button profileButton, toletButton, settingsButton, logoutButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileButton = (Button) findViewById(R.id.profileButton);
        toletButton = (Button) findViewById(R.id.toletButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(firebaseAuthStateListener != null){
            mAuth.removeAuthStateListener(firebaseAuthStateListener);
        }
    }

    public void onClickProfile(View view) {
        Intent informationIntent = new Intent(ProfileActivity.this, InformationActivity.class);
        startActivity(informationIntent);
    }

    public void onClickTolet(View view) {
        Intent  toletIntent = new Intent(ProfileActivity.this, ToLetActivity.class);
        startActivity(toletIntent);
    }

    public void onClickSettings(View view) {
        Intent settingsIntent = new Intent(ProfileActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void onClickLogOut(View view) {
        mAuth.signOut();
    }
}
