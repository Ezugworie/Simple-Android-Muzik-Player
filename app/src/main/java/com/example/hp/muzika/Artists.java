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

public class Artists extends Fragment {

    public ListView songView;
    public ArrayList<Song> songList = new ArrayList<>();

    //create an object of the track class to use its helper methods
    Tracks tracks = new Tracks();

    Uri musicUri;
    int music_column_index;
    Cursor musicCursor;
    MediaPlayer mMediaPlayer;

    //song
    String thisTitleWithoutExtension;
    int artistColumn;
    int dataColumnA;
    //onclick listener to play song
    public AdapterView.OnItemClickListener mPlaySongHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            music_column_index = dataColumnA;//musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

            musicCursor.moveToPosition(position);
            String filename = musicCursor.getString(music_column_index);
            String artistPath = musicCursor.getString(artistColumn);

            //go to the nowPlaying activity sending the songName and the songTitle
            Intent intent = new Intent(getContext(), NowPlaying.class);
            intent.putExtra("filePath", filename);
            intent.putExtra("artist", artistPath);
            startActivity(intent);


        }
    };
    long thisId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //call the getSongList Helper method of the Track class
        getSongList(songList);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.artist, container, false);
        songView = (ListView) view.findViewById(R.id.artist_song_list);

        //create a new instance of the adapter class and set it on the listView
        SongAdapter songAdapter = new SongAdapter(this.getActivity(), songList);
        songView.setAdapter(songAdapter);

        //play song when clicked on the fragment
        songView.setOnItemClickListener(mPlaySongHandler);

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

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED) {

            //sort the files alphabetically by artist
            String sortOrder = MediaStore.Audio.Media.ARTIST;

            musicCursor = musicResolver.query(musicUri,
                    projection, null, null, sortOrder);

            if (musicCursor != null) {

                musicCursor.moveToFirst();
                //get columns
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                dataColumnA = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                // int genreColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

                //add song to the list
                while (musicCursor.moveToNext()) {
                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    //  String thisGenre = musicCursor.getString(genreColumn);


                    songList.add(new Song(thisId, thisArtist, thisTitle));
                }
              ;

            }
        } else {
            getActivity().finish();
        }
    }


}
