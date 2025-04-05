package com.example.aerorush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class BirdSelection2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_selection2);

        ImageButton button1 = findViewById(R.id.buttonA);
        ImageButton button2 = findViewById(R.id.buttonB);
        ImageButton button3 = findViewById(R.id.buttonC);
        ImageButton button4 = findViewById(R.id.buttonD);


        button1.setOnClickListener(view -> selectBird(1));
        button2.setOnClickListener(view -> selectBird(2));
        button3.setOnClickListener(view -> selectBird(3));
        button4.setOnClickListener(view -> selectBird(4));
    }


    private void selectBird(int birdType) {
        Intent intent = new Intent(this, GameActivity2.class);
        intent.putExtra("BIRD_TYPE", birdType);
        startActivity(intent);
    }
}