package com.EdmonAntonyan.aerorush;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

public class MiddleActivity extends AppCompatActivity {
    public static boolean mode = false;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);


        findViewById(R.id.play1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MiddleActivity.this, BirdSelection.class));
            }
        });
        findViewById(R.id.play2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = true;
                startActivity(new Intent(MiddleActivity.this, BirdSelection2.class));
            }
        });



//        mediaPlayer.reset();
//
//        ImageButton btnVolume = findViewById(R.id.btnVolume);
//        findViewById(R.id.btnVolume).setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View view) {
//                if (isPlaying) {
//                    mediaPlayer.pause();
//
//
//                } else {
//                    mediaPlayer.start();
//
//                }
//                isPlaying = !isPlaying;
//
//
//            }
//        });

    }
}