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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CountdownFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CountdownFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountdownFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private final Handler h = new Handler();
    private final int delay = 1000; //milliseconds

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CountdownFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountdownFragment newInstance(String param1, String param2) {
        CountdownFragment fragment = new CountdownFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CountdownFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        /*SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
        Date launchDate = null;
        try {
            launchDate = dformat.parse("18-12-2015");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //     .getTime() does the conversion: Date --> long
        CountDownTimer cdt = new CountDownTimer(launchDate.getTime(), 1000) {

            public void onTick(long millisUntilFinished) {
                TextView countdownLabel = (TextView)getView().findViewById(R.id.countdownLabel);
                long seconds = (System.currentTimeMillis() - millisUntilFinished) / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
                countdownLabel.setText(time);
            }

            public void onFinish() {
                // TODO Auto-generated method stub

            }
        }.start();*/

        secondTick();
    }

    public void secondTick(){
        h.postDelayed(new Runnable() {
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
        }, delay);
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
