package com.example.dylan.finalprojectdylanalvin;

//By Alvin

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class YouTubeFragment extends Fragment {
    private SurfaceView surface = null;
    private SurfaceHolder holder = null;

    private OnFragmentInteractionListener mListener;

    public YouTubeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_you_tube, container, false);
        /*               -------------supposed to use this not setcontent view
        View rootView = inflater.inflate(R.layout.activity_play_video, container, false);
        return rootView;
        */
    }
    private MediaPlayer player;

    public void downloadAndPlayVideo(View view) {
        String url = "https://www.youtube.com/watch?v=sGbxmsDFVnE";

        surface = (SurfaceView)getView().findViewById(R.id.surfaceView);
        holder = surface.getHolder();

        try {
            if (player == null) {
                player = new MediaPlayer();
                player.setScreenOnWhilePlaying(true);
            } else {
                player.stop();
                player.reset();
            }

            Log.d("VideoPlayer", "Downloading video...");
            player.setDataSource(url);
            player.setDisplay(holder);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    int width=player.getVideoWidth();
                    int height=player.getVideoHeight();

                    if ((width != 0) && (height != 0)) {
                        holder.setFixedSize(width, height);
                        Log.d("VideoPlayer", "Playing video...");
                        player.start();
                    }
                }
            });
            player.prepareAsync();
        } catch (Exception e) {
            Log.e("VideoPlayer", "Exception:", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onYouTubeFragmentInteraction(String string);
    }

}
