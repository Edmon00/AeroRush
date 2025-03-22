package com.example.aerorush;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;



import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying = true;
    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED );
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.aerorushsong);



         ImageButton btnVolume = findViewById(R.id.btnVolume);
        findViewById(R.id.btnVolume).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    mediaPlayer.pause();


                } else {
                    mediaPlayer.start();

                }
                isPlaying = !isPlaying;


            }
        });

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MiddleActivity.class));
            }
        });

        findViewById(R.id.LoginSIgnup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("HighScore:" + prefs.getInt("highscore", 0 ));

        isMute = prefs.getBoolean("isMute", false);
    }
}