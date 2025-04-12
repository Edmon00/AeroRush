package com.example.aerorush;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import  static com.example.aerorush.GameActivity.birdType1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean  isGameOver = false;
    private volatile boolean isPlaying;
    private Background background1;
    private Background background2;
    private Flight flight;
    private int screenX, screenY, score = 0;
    private SharedPreferences prefs;
    private List<Bullet> bullets;
    private GameActivity activity;
    private int sound;
    private SoundPool soundPool;
    private Bird[] birds;
    private Paint paint;
    private Random random;
    int birdType;
    public static float screenRatioX, screenRatioY;
    public GameView(GameActivity activity, int screenX, int screenY) {

        super(activity);
        this.activity = activity;
        Bird bird = new Bird(getContext(), birdType);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;


        birdType = ((GameActivity) activity).getIntent().getIntExtra("BIRD_TYPE", 1);

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        flight = new Flight(this, screenY, getResources());
        bullets = new ArrayList<>();
        background2.x = screenX;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_GAME)
//                    .build();
//            soundPool = new SoundPool.Builder()
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        }else{
//            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//        }
//        sound = soundPool.load(activity,R.raw.shoot, 1 );
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
        while (isPlaying){
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
                    if (score > 1){
                        score = score - 2;

                    }else{
                        score = score;
                    }

                }
                int bound = (int) (30 * screenRatioX);
                bird.speed = random.nextInt(bound);

                if (bird.speed == 20 ){
                    bird.speed = (int) (10);
                }
                if (bird.speed < 10 ){
                    bird.speed = (int) (10);
                }

               bird.x = screenX;
               bird.y = random.nextInt(screenY - bird.height);

               bird.wasShot = false;
            }

            if (bird != null && flight != null &&
                    Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                isGameOver = true;
                break;
            }
        }

    }
//    private void draw(){
////        if (getHolder().getSurface().isValid()){
////            Canvas canvas = getHolder().lockCanvas();
////            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
////            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
////            if (isGameOver){
////                isPlaying = false;
////                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
////                getHolder().unlockCanvasAndPost(canvas);
////            }
////
////            for (Bird bird : birds){
////                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
////            }
////
////
////            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);
////
////            for (Bullet bullet : bullets){
////                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
////            }
////
////            getHolder().unlockCanvasAndPost(canvas);
////
////        }
////    }
    private void sleep(){
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
//    public void sleep() {
//        isPlaying = false;
//        try {
//            if (thread != null) {
//                thread.join(17);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
private void draw() {
    if (getHolder().getSurface().isValid()) {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
        canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
        for (Bird bird : birds) {
            canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
        }
        canvas.drawText(score + "", screenX / 2, 164, paint);
        if (isGameOver) {
            canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
            saveIfHighScore();
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

    private void saveIfHighScore() {
    if(prefs.getInt("highscore", 0)< score){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highscore", score);
        editor.apply();

    }
    }


    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }
    public void pause(){
        try {
            isPlaying = false;
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
        if(prefs.getBoolean("isMute", false)){
            soundPool.play(sound, 1, 1, 0, 0 , 1);
        }
    Bullet bullet = new Bullet(getResources());
    bullet.x = flight.x + flight.width;
    bullet.y = flight.y + (flight.height / 2);
    bullets.add(bullet);
    }


}
