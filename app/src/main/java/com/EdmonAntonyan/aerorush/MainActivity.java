package com.EdmonAntonyan.aerorush;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.EdmonAntonyan.aerorush.databinding.ActivityRegistrBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    //public static MediaPlayer mediaPlayer;
    public static boolean isPlaying = true;
    private boolean isMute;
    private FirebaseAuth auth;
    private ActivityRegistrBinding binding;
    private DatabaseReference databaseRef;
    private Button username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegistrBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.playMusic(this);

        
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        int score = prefs.getInt("highscore", 0);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Map<String, Object> update = new HashMap<>();
            update.put("highScore", score);

            FirebaseDatabase.getInstance("https://aerorushgame-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("users")
                    .child(uid)
                    .updateChildren(update);
        }
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }

        // Инициализация MediaPlayer
        //mediaPlayer = MediaPlayer.create(this, R.raw.aerorushsong);

        // Настройка кнопки для включения/выключения звука
        ImageButton btnVolume = findViewById(R.id.btnVolume);
        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    //mediaPlayer.pause();
                } else {
                   //mediaPlayer.start();
                }
                isPlaying = !isPlaying;
            }
        });
        findViewById(R.id.Leaderboard_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LiderboardActivity.class));
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
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
        });

        // Отображение high score, high level и last level
        TextView highScoreTxt = findViewById(R.id.highScoreTxt);

        highScoreTxt.setText("High Score: " + prefs.getInt("highscore", 0));



        TextView highLevelTxt = findViewById(R.id.highLevelTxt);
        highLevelTxt.setText("High Level: " + prefs.getInt("best_level", 0));

        TextView lastLevelTxt = findViewById(R.id.LastLevelTxt);
        lastLevelTxt.setText("Last Level: " + prefs.getInt("last_level", 0));

        // Проверка состояния звука (mute)
        isMute = prefs.getBoolean("isMute", false);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        username = findViewById(R.id.LoginSIgnup);

        if (user != null) {
            String uid = user.getUid();
            

            databaseRef = FirebaseDatabase
                    .getInstance("https://aerorushgame-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("users");

            databaseRef.child(uid).child("username").get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            String fetchedUsername = dataSnapshot.getValue(String.class);
                            username.setText(fetchedUsername);
                        } else {
                            username.setText("Username: not set");
                        }
                    });
                    
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApp.stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isMute) {
            //mediaPlayer.start();
        }
    }

}
