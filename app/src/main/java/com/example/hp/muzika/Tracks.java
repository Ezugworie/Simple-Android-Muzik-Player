package com.example.hp.muzika;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Tracks extends Fragment {

    public ListView songView;
    public ArrayList<Song> songList = new ArrayList<>();


    Uri musicUri;
    int music_column_index;
    int song_index;
    Cursor musicCursor;
    MediaPlayer mMediaPlayer;


    //song
    int artistColumn;
    int idColumn;
    int dataColumn;
    //helper method onclick listener to play song
    public AdapterView.OnItemClickListener mPlaySongHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


            //initialize the media Player

            music_column_index = dataColumn;//musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

            musicCursor.moveToPosition(position);
            String filename = musicCursor.getString(music_column_index);
            String artistPath = musicCursor.getString(artistColumn);

            //go to the now playing activity
            Intent intent = new Intent(getContext(), NowPlaying.class);
            intent.putExtra("filePath", filename);
            intent.putExtra("artist", artistPath);
         //   intent.putExtra("songIndex", position);
            intent.putExtra("playSongIndex", music_column_index);
          //  intent.putExtra("songListSize", songList.size() );
          //  intent.putExtra("songList", songList);
            startActivity(intent);




        }
    };
//    String thisArtist;
//    String thisData;
//    String thisTitle;
    long thisId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSongList(songList);
    }

    //@Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tracks, container, false);
        songView = (ListView) view.findViewById(R.id.track_song_list);

        //create a new instance of the adapter class and set it on the listView
        SongAdapter songAdapter = new SongAdapter(this.getActivity(), songList);
        songView.setAdapter(songAdapter);

        //play song when clicked on the fragment
        songView.setOnItemClickListener(mPlaySongHandler);
//        mMediaPlayer = new MediaPlayer();
        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //helper method to retrieve the audio file information
    public void getSongList(ArrayList<Song> songList) {
        //TODO retrieve song info


        ContentResolver musicResolver = getActivity().getContentResolver();

        final String[] projection = new String[]{
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID
        };

        musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //check if permission has been granted
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED) {

            //sort the files alphabetically by title
            String sortOrder = MediaStore.Audio.Media.TITLE;
            musicCursor = musicResolver.query(musicUri,
                    projection, null, null, sortOrder);

            if (musicCursor != null && musicCursor.moveToFirst()) {


                //get columns
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                idColumn = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                artistColumn = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                dataColumn = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                int displayNameColumn = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);

                //add song to the list
                do {
                    thisId = musicCursor.getLong(idColumn);
                    //String thisData = musicCursor.getString(dataColumn);
                    String thisTitle = musicCursor.getString(0);
                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisDisplayName = musicCursor.getString(displayNameColumn);


                    songList.add(new Song(thisId, thisDisplayName, thisArtist));

                }
                while (musicCursor.moveToNext());

            }
        } else {
            getActivity().finish();
        }

    }
}

