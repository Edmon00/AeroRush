package com.EdmonAntonyan.aerorush;

import static com.EdmonAntonyan.aerorush.GameView.screenRatioX;
import static com.EdmonAntonyan.aerorush.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Flight {
    public int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap flight1, flight2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
    private GameView gameView;
    private GameView2 gameView2;

    Flight (GameView gameView,  int screenY, Resources res) {

        this.gameView = gameView;

        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = flight1.getWidth();
        height = flight1.getHeight();

        width /= 3;
        height /= 3;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        if (width <= 0 || height <= 0) {
            width = 1;
            height = 1;
        }
        if (flight1 == null || flight2 == null) {
            throw new RuntimeException("Images not loaded!");
        } else {
            Log.d("Flight", "flight1 width: " + flight1.getWidth() + ", height: " + flight1.getHeight());
        }
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);
        dead = BitmapFactory.decodeResource(res, R.drawable.dead);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width,height, false );
        shoot2 = Bitmap.createScaledBitmap(shoot2, width,height, false );
        shoot3 = Bitmap.createScaledBitmap(shoot3, width,height, false );
        shoot4 = Bitmap.createScaledBitmap(shoot4, width,height, false );
        shoot5 = Bitmap.createScaledBitmap(shoot5, width,height, false );
        dead = Bitmap.createScaledBitmap(dead, width, height, false);


        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }
    Flight (GameView2 gameView2,  int screenY, Resources res) {

        this.gameView2 = gameView2;

        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = flight1.getWidth();
        height = flight1.getHeight();

        width /= 3;
        height /= 3;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        if (width <= 0 || height <= 0) {
            width = 1;
            height = 1;
        }
        if (flight1 == null || flight2 == null) {
            throw new RuntimeException("Images not loaded!");
        } else {
            Log.d("Flight", "flight1 width: " + flight1.getWidth() + ", height: " + flight1.getHeight());
        }
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);
        dead = BitmapFactory.decodeResource(res, R.drawable.dead);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width,height, false );
        shoot2 = Bitmap.createScaledBitmap(shoot2, width,height, false );
        shoot3 = Bitmap.createScaledBitmap(shoot3, width,height, false );
        shoot4 = Bitmap.createScaledBitmap(shoot4, width,height, false );
        shoot5 = Bitmap.createScaledBitmap(shoot5, width,height, false );
        dead = Bitmap.createScaledBitmap(dead, width, height, false);


        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }
    Bitmap getFlight(){
        if (toShoot != 0){
            if (shootCounter == 1){
                shootCounter++;
                return shoot1;
            }
            if (shootCounter == 2){
                shootCounter++;
                return shoot2;
            }
            if (shootCounter == 3){
                shootCounter++;
                return shoot3;
            }
            if (shootCounter == 4){
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toShoot--;

            if (gameView != null) {
                gameView.newBullet();
            }
            if (gameView2 != null) {
                gameView2.newBullet();
            }

            return shoot5;

        }
        if(wingCounter == 0){
            wingCounter ++;
            return flight1;
        }
        wingCounter --;
        return flight2;
    }
    Rect getCollisionShape(){
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead(){
        return dead;
    }

}
