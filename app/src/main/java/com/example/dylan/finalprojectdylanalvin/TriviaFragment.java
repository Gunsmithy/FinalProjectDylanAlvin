package com.example.dylan.finalprojectdylanalvin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriviaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriviaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriviaFragment extends Fragment     {




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
     * @return A new instance of fragment TriviaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TriviaFragment newInstance(String param1, String param2) {
        TriviaFragment fragment = new TriviaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TriviaFragment() {
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
        // GETTING BUTTON TO WORK
        View view = inflater.inflate(R.layout.fragment_trivia,
                container, false);

        Button button = (Button) view.findViewById(R.id.buttonStart);
        final Button a1button = (Button) view.findViewById(R.id.answer1Button);
        final Button a2button = (Button) view.findViewById(R.id.answer2Button);

        a1button.setVisibility(View.GONE);
        a2button.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textviewName= (TextView) getView().findViewById(R.id.NameText);
                EditText name = (EditText) getView().findViewById(R.id.NameText);
                name.getText().toString();
                Button button = (Button) v;
                button.setVisibility(View.GONE);
                a1button.setVisibility(View.VISIBLE);
                a2button.setVisibility(View.VISIBLE);
                textviewName.setVisibility(View.GONE);

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onTriviaFragmentInteraction(string);
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
        void onTriviaFragmentInteraction(String string);
    }


    /*
    private ArrayList<Trivia> trivia = null;
    private int triviaIndex = -1;

    TextView resultField = (TextView)findViewById(R.id.lblResult);


    TriviaHelper dbHelper = new TriviaHelper(this);
    dbHelper.deleteAllTrivia();

    Trivia one = dbHelper.createTrivia("Question1","Answer1","False");
    Trivia two = dbHelper.createTrivia("Question2","Answer2","False");
    Trivia three = dbHelper.createTrivia("Question3","Answer3","False");
    Trivia four = dbHelper.createTrivia("Question4","Answer4","False");
    Trivia five = dbHelper.createTrivia("Question5","Answer5","False");

    private void nextQuestion(View v)
    {
    nextQuestion();
    }

    private void nextQuestion()
    {
    this.triviaIndex++

    if (this.triviaIndex>=this.trivia.size())
    {
    this.contactIndex=0;
    }
        display(this.trivia.get(this.triviaIndex));
    }

    private void display(Trivia trivia)
    {
    TextView Question=(TextView)findViewById(R.id.InstructionsQuestions);

    Question.setText(trivia.getQuestion());
    a1button.setText(trivia.getAnswer());
    a2button.setText(Trivia.getAnswer());

    }

*/


}