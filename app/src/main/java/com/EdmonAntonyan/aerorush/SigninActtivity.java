package com.EdmonAntonyan.aerorush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SigninActtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_acttivity);

        EditText etUsername1 = findViewById(R.id.etUsername1);
        EditText etPassword1 = findViewById(R.id.etPassword1);
        Button btnsignin = findViewById(R.id.btnsignin);

        btnsignin.setOnClickListener(v -> {
            String username = etUsername1.getText().toString();
            String password = etPassword1.getText().toString();
            Log.d("Registration", "Username: " + username + ", Password: " + password);
        });
        findViewById(R.id.btnsignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActtivity.this, RegistrationActivity.class));
            }
        });


    }
}