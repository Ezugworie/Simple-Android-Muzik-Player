package com.example.hp.muzika;

public class Song {
    //song variables the holds the song properties
    private long mSongId;
    private String mSongTitle;
    private String mSongArtist;
    private String mSongGenre;

    //song location on the device
    private String mSongData;

    //default constructor
    public Song() {

    }

    //constructor for song class
    public Song(long id, String title, String artist) {
        mSongId = id;
        mSongTitle = title;
        mSongArtist = artist;
        //mSongData = songData;
    }

    public Song(long id, String artist, String title, String songGenre) {
        mSongId = id;
        mSongArtist = artist;
        mSongTitle = title;
        //  mSongData = songData;
        //  mSongGenre = songGenre;

    }

    //getter method for song id
    public long getmSongId() {
        return mSongId;
    }

    public void setmSongId(int mSongId) {
        this.mSongId = mSongId;
    }

    //getter method for song artist
    public String getmSongArtist() {
        return mSongArtist;
    }

    public void setmSongArtist(String mSongArtist) {
        this.mSongArtist = mSongArtist;
    }

    //getter method for song title
    public String getmSongTitle() {
        return mSongTitle;
    }

    public void setmSongTitle(String mSongTitle) {
        this.mSongTitle = mSongTitle;
    }

    //getter method for song data
    public String getmSongData() {
        return mSongData;
    }

    public void setmSongData(String mSongData) {
        this.mSongData = mSongData;
    }

    //getter method for song genre
    public String getmSongGenre() {
        return mSongGenre;
    }
}
