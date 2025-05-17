package com.EdmonAntonyan.aerorush;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Bird bird;
    public static int  birdType1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);

         birdType1 = getIntent().getIntExtra("BIRD_TYPE", 2);

        Log.d("GameActivity", "Context in onCreate: " + GameActivity.this);
        Log.d("GameActivity", "Creating Bird object with birdType: " + birdType1);

        bird = new Bird(GameActivity.this, birdType1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
