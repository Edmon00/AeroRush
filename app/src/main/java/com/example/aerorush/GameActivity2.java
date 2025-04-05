 package com.example.aerorush;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity2 extends AppCompatActivity {

    private GameView2 gameView2;
    private Bird bird;
    public static int  birdType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView2 = new GameView2(this, point.x, point.y);
        setContentView(gameView2);

        birdType = getIntent().getIntExtra("BIRD_TYPE", 2);

        Log.d("GameActivity", "Context in onCreate: " + GameActivity2.this);
        Log.d("GameActivity", "Creating Bird object with birdType: " + birdType);

        bird = new Bird(GameActivity2.this, birdType);


    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView2.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView2.resume();
    }
}
