package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

// User Information Class
public class InformationActivity extends AppCompatActivity {

    private TextView nameProfileTextView, emailProfileTextView, phoneProfileTextView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent informationIntent = new Intent(InformationActivity.this, LoginActivity.class);
                    startActivity(informationIntent);
                    finish();
                }
            }
        };

        nameProfileTextView = (TextView) findViewById(R.id.nameProfileTextView);
        emailProfileTextView = (TextView) findViewById(R.id.emailProfileTextView);
        phoneProfileTextView = (TextView) findViewById(R.id.phoneProfileTextView);

        getUserInfo();
    }

    //gets user info from Firebase
    private void getUserInfo() {
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        currentUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(InformationActivity.this, "In the onDataChange", Toast.LENGTH_SHORT).show();
                String name = dataSnapshot.child("Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String phone = dataSnapshot.child("Phone").getValue().toString();

                Toast.makeText(InformationActivity.this, name + "'s Information", Toast.LENGTH_LONG).show();

                nameProfileTextView.setText(name);
                emailProfileTextView.setText(email);
                phoneProfileTextView.setText(phone);
                //Toast.makeText(InformationActivity.this, "End of the method", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InformationActivity.this, "FML", Toast.LENGTH_LONG).show();
                Log.e("ERROR", "Couldn't connect to the server");
                finish();
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
