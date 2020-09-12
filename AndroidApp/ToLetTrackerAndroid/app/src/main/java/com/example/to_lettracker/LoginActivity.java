package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// User Login Class
public class LoginActivity extends AppCompatActivity {

    private TextView emailTextView, passwordTextView;
    private Button loginButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent profileActivity = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(profileActivity);
                    finish();

                }
            }
        };

        emailTextView = (TextView) findViewById(R.id.emailTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);

        loginButton = (Button) findViewById(R.id.loginButton);
    }

    public void onClickLogin(View view) {
        final String email = emailTextView.getText().toString();
        final String password = passwordTextView.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e("LoginActivity", "onClickLogin" + task.getException());
                if(!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Email/Password didn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void regClickHere(View view) {
        Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
