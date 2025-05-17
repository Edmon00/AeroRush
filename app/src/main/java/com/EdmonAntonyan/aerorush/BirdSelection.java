package com.EdmonAntonyan.aerorush;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class BirdSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_selection);

        ImageButton button1 = findViewById(R.id.button1);
        ImageButton button2 = findViewById(R.id.button2);
        ImageButton button3 = findViewById(R.id.button3);
        ImageButton button4 = findViewById(R.id.button4);


        button1.setOnClickListener(view -> selectBird(1));
        button2.setOnClickListener(view -> selectBird(2));
        button3.setOnClickListener(view -> selectBird(3));
        button4.setOnClickListener(view -> selectBird(4));
    }


    private void selectBird(int birdType) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("BIRD_TYPE", birdType);
        startActivity(intent);
    }
}