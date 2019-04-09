package com.example.hp.muzika;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyService extends Service implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnPreparedListener {

    NowPlaying nowPlaying = new NowPlaying();

    public void onAudioFocusChange(int focusChange) {
        //TODO do something based on focus change...
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                //if audio focus is gain resume playback
                if (nowPlaying.mMediaPlayer == null) {
                    //initiate mediaplayer

                } else if (!nowPlaying.mMediaPlayer.isPlaying()) {
                    nowPlaying.mMediaPlayer.start();
                    nowPlaying.mMediaPlayer.setVolume(1.0f, 1.0f);
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                //Lost focus for an unKnown amount of time: stop the playback and release the media player
                if (nowPlaying.mMediaPlayer.isPlaying()) {
                    nowPlaying.mMediaPlayer.stop();
                    nowPlaying.mMediaPlayer.release();
                    nowPlaying.mMediaPlayer = null;
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                /** focus for a short amount of time, but we have to stop playback.
                 * We don't release mediaplayer because playback is likely to resume
                 * */
                if (nowPlaying.mMediaPlayer.isPlaying()) {
                    nowPlaying.mMediaPlayer.pause();
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //Lost audio focus for a short time,  but
                //it's ok to keep playing at an attenuated level
                if (nowPlaying.mMediaPlayer.isPlaying()) {
                    nowPlaying.mMediaPlayer.setVolume(0.1f, 0.1f);

                }
                break;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
