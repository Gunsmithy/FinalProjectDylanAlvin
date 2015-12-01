package com.example.dylan.finalprojectdylanalvin;

//By Dylan

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SettingsFragment extends Fragment {

    private int radius;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
        radius = 0;
    }

    public void setmListener(OnFragmentInteractionListener listener){
        this.mListener = listener;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText radiusText = (EditText) getView().findViewById(R.id.radiusText);
        radiusText.setText(String.valueOf(this.radius));
    }

    @Override
    public void onStop() {
        super.onStop();
        EditText radiusText = (EditText) getView().findViewById(R.id.radiusText);
        mListener.onSettingsFragmentInteraction(radiusText.getText().toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSettingsFragmentInteraction(String string);
    }

}
