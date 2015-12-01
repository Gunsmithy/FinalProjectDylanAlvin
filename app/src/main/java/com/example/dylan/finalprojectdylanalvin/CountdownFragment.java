package com.example.dylan.finalprojectdylanalvin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CountdownFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private final Handler h = new Handler();
    private final int delay = 1000; //milliseconds
    private Runnable myRunnable = new Runnable() {
        public void run() {
            Time now = new Time();
            now.setToNow();
            Time launch = new Time();
            launch.set(18, 11, 2015);
            TextView countdownLabel = (TextView) getView().findViewById(R.id.countdownLabel);
            long seconds = (launch.toMillis(true) - now.toMillis(true)) / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Star_Jedi_Rounded.ttf");
            countdownLabel.setTypeface(tf);
            countdownLabel.setTextColor(Color.parseColor("#FCDF2B"));
            countdownLabel.setTextSize(50);
            countdownLabel.setText(time);
            h.postDelayed(this, delay);
        }
    };

    public CountdownFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        secondTick();
    }

    public void secondTick() {
        h.postDelayed(myRunnable, delay);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countdown, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onCountdownFragmentInteraction(string);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        h.removeCallbacks(myRunnable);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCountdownFragmentInteraction(String string);
    }

}
