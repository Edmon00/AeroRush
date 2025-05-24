package com.EdmonAntonyan.aerorush;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

public class MyApp extends Application {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.aerorushsong);
            mediaPlayer.setLooping(true);
        }
    }

    public static void playMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.aerorushsong);
            mediaPlayer.setLooping(true);
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public static boolean isMusicPlaying() {
        return isPlaying;
    }
}