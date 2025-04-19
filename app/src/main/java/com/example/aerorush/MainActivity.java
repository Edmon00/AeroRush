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


//public class MainActivity extends AppCompatActivity {
//
//    public static MediaPlayer mediaPlayer;
//    public static boolean isPlaying = true;
//    private boolean isMute;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED );
//        setContentView(R.layout.activity_main);
//        mediaPlayer = MediaPlayer.create(this, R.raw.aerorushsong);
//
//
//
//         ImageButton btnVolume = findViewById(R.id.btnVolume);
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
//
//        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MiddleActivity.class));
//            }
//        });
//
//        findViewById(R.id.LoginSIgnup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
//            }
//        });
//        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
//        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
//        highScoreTxt.setText("High Score:" + prefs.getInt("highscore", 0 ));
//        TextView highLevelTxt = findViewById(R.id.highLevelTxt);
//        SharedPreferences prefs1 = getSharedPreferences("game", MODE_PRIVATE);
//        highLevelTxt.setText("High Level:" + prefs.getInt("highLevel", 0));
//        TextView lastLevelTxt = findViewById(R.id.LastLevelTxt);
//        SharedPreferences prefs2 = getSharedPreferences("game", MODE_PRIVATE);
//        lastLevelTxt.setText("Last Level:" + prefs.getInt("lastLevel", 0));
//
//
//
//        isMute = prefs.getBoolean("isMute", false);
//    }
//}
public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying = true;
    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.aerorushsong);

        // Настройка кнопки для включения/выключения звука
        ImageButton btnVolume = findViewById(R.id.btnVolume);
        btnVolume.setOnClickListener(new View.OnClickListener() {
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

        // Настройка кнопки для начала игры
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MiddleActivity.class));
            }
        });

        // Настройка кнопки для регистрации/входа
        findViewById(R.id.LoginSIgnup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        // Отображение high score, high level и last level
        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("High Score: " + prefs.getInt("highscore", 0));

        TextView highLevelTxt = findViewById(R.id.highLevelTxt);
        highLevelTxt.setText("High Level: " + prefs.getInt("best_level", 0));

        TextView lastLevelTxt = findViewById(R.id.LastLevelTxt);
        lastLevelTxt.setText("Last Level: " + prefs.getInt("last_level", 0));

        // Проверка состояния звука (mute)
        isMute = prefs.getBoolean("isMute", false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Приостановка воспроизведения музыки при уходе в фоновый режим
        if (isPlaying) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Восстановление воспроизведения музыки при возвращении в активное состояние
        if (!isMute) {
            mediaPlayer.start();
        }
    }
}
