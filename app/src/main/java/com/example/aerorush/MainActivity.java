package com.example.aerorush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;



import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED );
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.aerorushsong);

            mediaPlayer.start();

        Button btnVolume = findViewById(R.id.btnVolume);
        findViewById(R.id.btnVolume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    mediaPlayer.pause(); // Остановка музыки
                    btnVolume.setText("Volume Off"); // Изменение текста кнопки
                } else {
                    mediaPlayer.start(); // Возобновление музыки
                    btnVolume.setText("Volume Up"); // Изменение текста кнопки
                }
                isPlaying = !isPlaying;

            }
        });

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        findViewById(R.id.LoginSIgnup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

    }
}