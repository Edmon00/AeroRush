package com.example.aerorush;

import static com.example.aerorush.GameView.screenRatioX;
import static com.example.aerorush.GameView.screenRatioY;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Rect;
import android.util.Log;
import  static com.example.aerorush.GameActivity2.birdType;
import  static com.example.aerorush.GameActivity.birdType1;
import  static com.example.aerorush.MiddleActivity.mode;

public class Bird {
    public int speed = 20;
    public boolean wasShot = true;
    int frameCounter = 0;
    int x = 0, y, width, height, birdCounter = 1;
    public Bitmap bird1, bird2, bird3, bird4;

    public Bird(Context context, int birdType) {
        Log.d("Bird Constructor", "Context received: " + context);
        Resources res = context.getResources();
        setBirdType(birdType, res);
    }


    public void setBirdType(int birdType, Resources res) {
        if (mode) {
            birdType = GameActivity2.birdType;
        }else{
            birdType = GameActivity.birdType1;
        }

        switch (birdType) {
            case 1:
                bird1 = BitmapFactory.decodeResource(res, R.drawable.agravverj);
                bird2 = BitmapFactory.decodeResource(res, R.drawable.agraverj1);
                bird3 = BitmapFactory.decodeResource(res, R.drawable.agravverj2);
                bird4 = BitmapFactory.decodeResource(res, R.drawable.agravverj3);
                break;
            case 2:
                bird1 = BitmapFactory.decodeResource(res, R.drawable.arciv);
                bird2 = BitmapFactory.decodeResource(res, R.drawable.arciv1);
                bird3 = BitmapFactory.decodeResource(res, R.drawable.arciv2);
                bird4 = BitmapFactory.decodeResource(res, R.drawable.arciv1);
                break;
            case 3:
                bird1 = BitmapFactory.decodeResource(res, R.drawable.chayka);
                bird2 = BitmapFactory.decodeResource(res, R.drawable.chayka1);
                bird3 = BitmapFactory.decodeResource(res, R.drawable.chayka2);
                bird4 = BitmapFactory.decodeResource(res, R.drawable.chayka1);
                break;
            case 4:
                bird1 = BitmapFactory.decodeResource(res, R.drawable.trchun);
                bird2 = BitmapFactory.decodeResource(res, R.drawable.trchun1);
                bird3 = BitmapFactory.decodeResource(res, R.drawable.trchun2);
                bird4 = BitmapFactory.decodeResource(res, R.drawable.trchun1);
                break;
        }

        if (bird1 == null || bird2 == null || bird3 == null || bird4 == null) {
            Log.e("Bird", "Error: One or more bird images failed to load.");
            // Optionally, set a default image if any of them is null
            bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
            bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
            bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
            bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);
        }

        width = bird1.getWidth();
        height = bird1.getHeight();
        if(width > 0 && height > 0) {
            width = 150;// (int) (width * screenRatioX);
            height = 175;//(int) (height * screenRatioY);


            bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
            bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
            bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
            bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);
        }else{
            width = 175;// (int) (width * screenRatioX);
            height = 200;
        }
        y = -height;
    }


    Bitmap getBird() {
        frameCounter++;
        if (frameCounter < 10) {
            return getCurrentBird();
        }
        frameCounter = 0;
        birdCounter++;
        if (birdCounter > 4) {
            birdCounter = 1;
        }
        return getCurrentBird();
    }

    private Bitmap getCurrentBird() {
        switch (birdCounter) {
            case 1:
                return bird1;
            case 2:
                return bird2;
            case 3:
                return bird3;
            default:
                return bird4;
        }
    }
    Rect getCollisionShape(){
        return new Rect(x, y, x + width, y + height);
    }
    private void waitobject(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
