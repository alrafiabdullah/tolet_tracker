package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// User Settings View
public class SettingsActivity extends AppCompatActivity {
    private Button changeEmailButton, changePhoneButton;
    private EditText emailChangeEditText, phoneChangeEditText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent informationIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(informationIntent);
                    finish();
                }
            }
        };

        changeEmailButton = (Button) findViewById(R.id.changeEmailButton);
        changePhoneButton = (Button) findViewById(R.id.changePhoneButton);

        emailChangeEditText = (EditText) findViewById(R.id.emailChangeEditText);
        phoneChangeEditText = (EditText) findViewById(R.id.phoneChangeEditText);

    }

    // Changes Email
    public void onClickChangeEmail(View view) {
        changeEmail();
        Intent profileIntent = new Intent(SettingsActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
        finish();
    }

    // Changes Phone Number
    public void onClickChangePhone(View view) {
        changePhone();
        Intent profileIntent = new Intent(SettingsActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
        finish();
    }

    public void changeEmail() {
        final String email = emailChangeEditText.getText().toString();
        final String userID = mAuth.getCurrentUser().getUid();

        final DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        currentUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserDb.child("Email").setValue(email);
                Toast.makeText(SettingsActivity.this,"Email Changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingsActivity.this,"Email is Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePhone() {
        final String phone = phoneChangeEditText.getText().toString();
        final String userID = mAuth.getCurrentUser().getUid();

        final DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        currentUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserDb.child("Phone").setValue(phone);
                Toast.makeText(SettingsActivity.this,"Phone Number Changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingsActivity.this,"Phone Number is Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
