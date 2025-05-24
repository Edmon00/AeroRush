package com.EdmonAntonyan.aerorush;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.EdmonAntonyan.aerorush.databinding.ActivityRegistrBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrActtivity extends AppCompatActivity {

    private ActivityRegistrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrActtivity.this, LoginActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.registrEmailEt.getText().toString().trim();
                String password = binding.registrPasswordEt.getText().toString().trim();
                String username = binding.usernameEt.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty() || username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        // Создаем карту данных
                                        HashMap<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("email", email);
                                        userInfo.put("username", username);

                                        // Сохраняем в Firebase
                                        FirebaseDatabase.getInstance("https://aerorushgame-default-rtdb.europe-west1.firebasedatabase.app/")
                                                .getReference("users")
                                                .child(uid)
                                                .setValue(userInfo)
                                                .addOnSuccessListener(unused -> {
                                                    Log.d("FIREBASE", "Данные успешно записаны");
                                                    Toast.makeText(getApplicationContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();

                                                    // Переход в MainActivity только после записи
                                                    startActivity(new Intent(RegistrActtivity.this, MainActivity.class));
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("FIREBASE", "Ошибка записи: " + e.getMessage());
                                                    Toast.makeText(getApplicationContext(), "Ошибка записи: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                });

                                    } else {
                                        Log.e("REGISTER_ERROR", "Ошибка регистрации: ", task.getException());
                                        Toast.makeText(getApplicationContext(), "Ошибка регистрации: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}