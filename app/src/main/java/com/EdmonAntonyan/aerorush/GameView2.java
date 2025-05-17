package com.EdmonAntonyan.aerorush;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import static com.EdmonAntonyan.aerorush.GameView.screenRatioX;
import static com.EdmonAntonyan.aerorush.GameView.screenRatioY;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView2 extends SurfaceView implements Runnable {

    private GameActivity2 activity;
    private int level = 1;
    private long levelUpTime = 0;
    private int nextLevelScore = 10, highSpeed = 8, lowSpeed = 5;
    private Thread thread;
    private int screenX, screenY, score = 0;
    private boolean  isGameOver = false;
    private volatile boolean isPlaying1;
    private Flight flight;
    int requiredScore = 10 + (level - 1) * 5;
    int birdType;
    private Paint paint;
    private Bird[] birds;
    private List<Bullet> bullets;
    private SharedPreferences prefs;
    private Random random;

    private Background background1, background2;
    private boolean levelUp = false;

    public GameView2(Activity activity, int screenX, int screenY) {
        super(activity);
        this.activity = (GameActivity2) activity;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        flight = new Flight(this, screenY, getResources());
        bullets = new ArrayList<>();
        background2.x = screenX;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.GRAY);

        birds = new Bird[4];

        for (int i = 0; i < 4; i++ ){
            int birdType = i + 1;
            birds[i] = new Bird(getContext(), birdType);
        }
        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying1){
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        background2.x -= 10 * screenRatioX;
        background1.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2 .x = screenX;
        }

        if (flight.isGoingUp) {
            flight.y -= 30 * screenRatioY;
        }else{
            flight.y += 30 * screenRatioY;
        }
        if (flight.y < 0) {
            flight.y = 0;
        }
        if (flight.y > screenY - flight.height){
            flight.y = screenY - flight.height;
        }
        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets){
            if (bullet.x > screenX){
                trash.add(bullet);
            }
            bullet.x += 50 * screenRatioX;

            for (Bird bird : birds){
                if(Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())){
                    score++;
                    bird.x = -500;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;
                }
            }
        }
        for (Bullet bullet : trash){
            bullets.remove(bullet);
        }

        for (Bird bird : birds){
            bird.x -= bird.speed;
            if (bird.x + bird.width < 0){

                if (!bird.wasShot){
                    isGameOver = true;
//                    if (score > 1){
//                        score = score - 2;
//
//                    }else{
//                        score = score;
//                    }

                }
                int bound = (int) (30 * screenRatioX);
                bird.speed = lowSpeed + random.nextInt(highSpeed - lowSpeed + 1);

//                if (bird.speed == highSpeed ){
//                    bird.speed = (int) (highSpeed - 3);
//                }
//                if (bird.speed < 10 ){
//                    bird.speed = (int) (10);
//                }

                bird.x = screenX;
                bird.y = random.nextInt(screenY - bird.height);

                bird.wasShot = false;
            }

            if (bird != null && flight != null &&
                    Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                isGameOver = true;
                break;
            }
            if (score >= nextLevelScore) {
                level++;
                score = 0;
                nextLevelScore += 5;
                lowSpeed += 3;
                highSpeed += 3;
                waitLevel();

                updateBestLevel();


                    bird.speed += 5;



                 Log.d("LEVEL_UP", "Level: " + level);
            }
        }

    }

    private void waitLevel() {
        levelUp = true;
        levelUpTime = System.currentTimeMillis();
    }
    private void draw(){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            for (Bird bird : birds) {
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            }
            canvas.drawText("Score: " + score + "   Level: " + level, screenX / 2 - 500, 164, paint);
            if (levelUp) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - levelUpTime < 2000) {
                    Paint levelPaint = new Paint();
                    levelPaint.setTextSize(150);
                    levelPaint.setColor(Color.YELLOW);
                    levelPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText("LEVEL " + level, screenX / 2, screenY / 2, levelPaint);
                } else {
                    levelUp = false;
                }
            }
            if (isGameOver) {
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                //saveIfHighScore();
                updateBestLevel();
                getHolder().unlockCanvasAndPost(canvas);
                waitExit();
                return;
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitExit() {
        try {
            Thread.sleep(2000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    private void saveIfHighScore() {
//        if(prefs.getInt("highscore", 0)< score){
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putInt("highscore", score);
//            editor.apply();
//
//        }
//    }
    private void updateBestLevel() {
        //SharedPreferences prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int bestLevel = prefs.getInt("best_level", 1);
        if (level > bestLevel) {
            editor.putInt("best_level", level);
        }
        editor.putInt("last_level", level);
        editor.apply();
    }

    public void resume(){
        isPlaying1 = true;
        thread = new Thread(this);
        thread.start();

    }
    private void sleep(){
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void pause(){
        try {
            isPlaying1 = false;
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getX()< screenX / 2){
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if (event.getX() > screenX / 2){
                    flight.toShoot++;
                }
                break;
        }
        return true;
    }
    public void newBullet() {

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);
    }
}
