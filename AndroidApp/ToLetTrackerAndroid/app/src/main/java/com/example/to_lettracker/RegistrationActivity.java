package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

// User Registration Class
public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, phoneEditText;
    private Button regSubmitButton;
    private View firstView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Log.i("onCreate","Registration");

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent profileActivity = new Intent(RegistrationActivity.this, ProfileActivity.class);
                    startActivity(profileActivity);
                    finish();
                    return;
                }
            }
        };

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);

        regSubmitButton = (Button) findViewById(R.id.regSubmitButton);
    }

    // Registering User
    public void regSubmit(View view) {

        final String name = nameEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String phone = phoneEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("RegistrationActivity", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();

                            DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                            Map<String,String> userData = new HashMap<String, String>();
                            userData.put("Name", name);
                            userData.put("Email", email);
                            userData.put("Phone", phone);

                            userDb.setValue(userData);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("RegistrationActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void loginClickHere(View view) {
        Intent registrationIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(registrationIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthStateListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthStateListener);
        }
    }
}