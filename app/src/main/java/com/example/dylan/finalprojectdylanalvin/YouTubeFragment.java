package com.example.dylan.finalprojectdylanalvin;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YouTubeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YouTubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//supposed to be TRAILERFRAGMENT
public class YouTubeFragment extends Fragment {
    private SurfaceView surface = null;
    private SurfaceHolder holder = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YouTubeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YouTubeFragment newInstance(String param1, String param2) {
        YouTubeFragment fragment = new YouTubeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public YouTubeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onYouTubeFragmentInteraction(string);

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onYouTubeFragmentInteraction(String string);
    }

}
