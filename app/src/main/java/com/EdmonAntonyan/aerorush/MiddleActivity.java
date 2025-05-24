package com.EdmonAntonyan.aerorush;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


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
                startActivity(new Intent(MiddleActivity.this, GameActivity.class));
            }
        });
        findViewById(R.id.play2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = true;
                startActivity(new Intent(MiddleActivity.this, GameActivity2.class));
            }
        });
        ImageButton volumebtn = findViewById(R.id.btnVolume);
        volumebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.isMusicPlaying()) {
                    MyApp.stopMusic();
                    volumebtn.setImageResource(R.drawable.baseline_volume_off_24);
                } else {
                    MyApp.playMusic(v.getContext());
                    volumebtn.setImageResource(R.drawable.baseline_volume_up_24);
                }
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
    @Override
    protected void onPause() {
        super.onPause();
        MyApp.stopMusic();
    }
}