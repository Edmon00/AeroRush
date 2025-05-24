package com.EdmonAntonyan.aerorush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountActivity extends AppCompatActivity {

    private TextView emailTextView, usernameTextView;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account); // use your XML layout here

        emailTextView = findViewById(R.id.emailTextView);
        usernameTextView = findViewById(R.id.usernameTextView);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.go_to_login_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });

        if (user != null) {
            String uid = user.getUid();
            String email = user.getEmail();
            emailTextView.setText("Email: " + email);

            databaseRef = FirebaseDatabase
                    .getInstance("https://aerorushgame-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("users");

            databaseRef.child(uid).child("username").get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            String fetchedUsername = dataSnapshot.getValue(String.class);
                            usernameTextView.setText("Username: " + fetchedUsername);
                        } else {
                            usernameTextView.setText("Username: not set");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AccountActivity.this, "Failed to load username", Toast.LENGTH_SHORT).show();
                        Log.e("FIREBASE", "Error reading username: " + e.getMessage());
                    });
        }
    }
}