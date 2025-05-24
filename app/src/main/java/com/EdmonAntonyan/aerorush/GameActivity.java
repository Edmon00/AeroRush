package com.EdmonAntonyan.aerorush;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Bird bird;
    public static int  birdType1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);



        Button upBtn = findViewById(R.id.up_btn);
        Button fireBtn = findViewById(R.id.fire_btn);

        FrameLayout gameContainer = findViewById(R.id.game_container);
        gameContainer.addView(gameView, 0);


        upBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    gameView.setGoingUp(true);
                    break;
                case MotionEvent.ACTION_UP:
                    gameView.setGoingUp(false);
                    break;
            }
            return true;
        });

        fireBtn.setOnClickListener(v -> {
            gameView.newBullet();
        });


        birdType1 = getIntent().getIntExtra("BIRD_TYPE", 4);

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
