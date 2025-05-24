 package com.EdmonAntonyan.aerorush;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

 public class GameActivity2 extends AppCompatActivity {

    private GameView2 gameView2;
    private Bird bird;
    public static int  birdType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(gameView2);
        setContentView(R.layout.activity_game2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView2 = new GameView2(this, point.x, point.y);

        Button upBtn = findViewById(R.id.up_btn);
        Button fireBtn = findViewById(R.id.fire_btn);

        FrameLayout gameContainer = findViewById(R.id.game_container);
        gameContainer.addView(gameView2, 0);

        upBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    gameView2.setGoingUp(true);
                    break;
                case MotionEvent.ACTION_UP:
                    gameView2.setGoingUp(false);
                    break;
            }
            return true;
        });

        fireBtn.setOnClickListener(v -> {
            gameView2.newBullet();
        });

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
