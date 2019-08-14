package com.example.hp.muzika;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songs;
    private LayoutInflater songInf;

    public SongAdapter(Context context, ArrayList<Song> theSongs) {
        songs = theSongs;
        songInf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //map to song layout
        LinearLayout songLay = (LinearLayout) songInf.inflate(R.layout.list_item_track, viewGroup, false);

        //get title and artist views
        TextView songView = (TextView) songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView) songLay.findViewById(R.id.song_artist);

        //get the song using position
        Song currentSong = songs.get(position);

        //set the title and artist strings
        songView.setText(currentSong.getmSongTitle());
        artistView.setText(currentSong.getmSongArtist());

        //set position as a tag
        songLay.setTag(position);
        return songLay;
    }


}
