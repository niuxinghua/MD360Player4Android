package com.asha.md360player4android;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

/**
 * Created by hzqiujiadi on 16/1/22.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class MediaPlayerActivity extends Activity implements MediaPlayer.OnPreparedListener {

    private static final String TAG = "MediaPlayerActivity";
    protected MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
    }

    public MediaPlayer getPlayer() {
        return mPlayer;
    }

    public void play() {
        if (mPlayer == null) return;
        if (mPlayer.isPlaying()) mPlayer.stop();
        try {
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.demo);
            if (afd == null) return;
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void onPlayButtonClicked(View view) {
        play();
    }
}