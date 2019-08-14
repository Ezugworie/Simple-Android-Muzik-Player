package com.example.hp.muzika;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


public class NowPlaying extends AppCompatActivity {

    public MediaPlayer mMediaPlayer = new MediaPlayer();
    String filename;
    String artistNamePath;
    String songTitleWithoutExtension;
    int SONG_EXTENSION = 4;
    ImageView playButton;
    ImageView previousSongButtton;
    ImageView nextSongButton;
    int currentSongIndex;
    String songListSize;
    TextView currentTimeTextView;
    TextView stopTimeTextView;
    SeekBar seekBar;
    private Handler mHandler;
    private Runnable mRunnable;
    public ArrayList<String> songList = new ArrayList<>();

    // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    ///mediaPlayer.setDataSource(getApplicationContext(),contentUri);
//        mediaPlayer.prepareAsync();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing);


        final ImageView playingImage = (ImageView) findViewById(R.id.playingImage);
        final Animation imageAnimator = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.now_playing);
        ///imageAnimator.setDuration(999999999);
        // imageAnimator.start();
        playingImage.setAnimation(imageAnimator);


        //get the music file name from parent activity throug an intent
        Intent intent = getIntent();
        filename = intent.getStringExtra("filePath");
        artistNamePath = intent.getStringExtra("artist");
        currentSongIndex = intent.getIntExtra("playSongIndex", currentSongIndex);
      // songListSize = intent.getStringExtra("songListSize");
     //  songList = intent.getStringArrayListExtra("songList" );
//   Tracks tracks = new Tracks();
//   tracks.songList.size();

        //get the song title from its path string
        String songTitleFromPath = filename.substring(filename.lastIndexOf("/") + 1);
        Log.v("filenme", filename);


        //call the helper method to truncate the mp3 extension
        truncateExtension(songTitleFromPath);


        //set the title and artist of the song
        TextView songTitle = (TextView) findViewById(R.id.current_song);
        TextView songArtist = (TextView) findViewById(R.id.current_song_artist);
        final TextView currentTime = (TextView) findViewById(R.id.current_time);
        TextView stopTime = (TextView) findViewById(R.id.stop_time);
        playButton = (ImageView) findViewById(R.id.btnPlay);
        previousSongButtton = (ImageView) findViewById(R.id.btnPreviousSong);
        nextSongButton = (ImageView) findViewById(R.id.btnNextSong);

        songTitle.setText(songTitleWithoutExtension);
        songArtist.setText(artistNamePath);


        //initialize the mediaPlayer

        try {
            mMediaPlayer.setDataSource(filename);

            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (mMediaPlayer.isPlaying()) {

                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer.start();
                    playButton.setImageDrawable((getDrawable(R.drawable.pause)));
                    //call the seek bar when the song starts to play
                } else {
                    mMediaPlayer.start();
                    playButton.setImageDrawable((getDrawable(R.drawable.pause)));
                }
            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playButton.setImageDrawable((getDrawable(R.drawable.play)));
            }
        });

        //handles the pause and play button including animated image
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    //playingImage.clearAnimation();
                    playButton.setImageDrawable((getDrawable(R.drawable.play)));
                } else if (!mMediaPlayer.isPlaying()){
                    mMediaPlayer.start();
                   // playingImage.setAnimation(imageAnimator);
                    playButton.setImageDrawable((getDrawable(R.drawable.pause)));
                }
            }
        });


        //handle the next button
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //first convert the @currentSongIndex from String object to primitive integer
                //long currentSongIndexToInteger = Integer.parseInt(currentSongIndex);
                int songListSizeToInteger = Integer.parseInt(songListSize);
                //TODO convert currentSongIndex and songListSize from String Integer
                if (currentSongIndex < (songListSizeToInteger -1 )){

                }
            }
        });

        //set the song title
        setTitle(songTitleWithoutExtension);
    }

//    public void playSong(int songIndex){
//        //play song
//        try{
//            //get the current songIndex in integer
//         //   int currentSongIndexToString = Integer.parseInt(currentSongIndex);
//            mMediaPlayer.reset();
//            mMediaPlayer.setDataSource(musicCursor.getString(currentSongIndex));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();

        //playingImage.clearAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mMediaPlayer.isPlaying()) {
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//
//    }


    //hepler method to truncate the extension of the songName
    public String truncateExtension(String mSongTitleFromPath) {

        //get the length of the song ONLY to display the song without its extension (mp3,3gp, ect)
        int filenameLength = mSongTitleFromPath.length();
        filenameLength -= SONG_EXTENSION;
        songTitleWithoutExtension = mSongTitleFromPath.substring(0, filenameLength);

        return songTitleWithoutExtension;
    }


}

