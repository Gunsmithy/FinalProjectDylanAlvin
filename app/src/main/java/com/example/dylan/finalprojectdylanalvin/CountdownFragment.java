package com.example.dylan.finalprojectdylanalvin;

//By Dylan

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountdownFragment extends Fragment {

    private final Handler h = new Handler();
    private final int delay = 1000; //milliseconds
    private Runnable myRunnable = new Runnable() {
        public void run() {
            //Get the current time
            Time now = new Time();
            now.setToNow();
            Time launch = new Time();
            launch.set(18, 11, 2015);
            TextView countdownLabel = (TextView) getView().findViewById(R.id.countdownLabel);
            //Calculate the digits
            long seconds = (launch.toMillis(true) - now.toMillis(true)) / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            //Set the current time on the countdown
            String time = days + ":" + String.format("%02d", hours % 24) + ":" + String.format("%02d", minutes % 60) + ":" + String.format("%02d", seconds % 60);
            //Style it up
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Star_Jedi_Rounded.ttf");
            countdownLabel.setTypeface(tf);
            countdownLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            countdownLabel.setTextSize(50);
            countdownLabel.setText(time);
            //Repeat until stopped
            h.postDelayed(this, delay);
        }
    };

    public CountdownFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //secondTick();
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

    @Override
    public void onStart() {
        super.onStart();
        //Start the countdown
        secondTick();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stop the countdown
        h.removeCallbacks(myRunnable);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
