package com.example.dylan.finalprojectdylanalvin;

//By Alvin

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaFragment extends Fragment     {

    private OnFragmentInteractionListener mListener;

    public TriviaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                //button changes postion when visibility changes
                a1button.setVisibility(View.VISIBLE);
                a2button.setVisibility(View.VISIBLE);
                textviewName.setVisibility(View.GONE);

            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTriviaFragmentInteraction(String string);
    }



    private ArrayList<Trivia> trivia = null;
    private int triviaIndex = -1;
/*
    TextView resultField = (TextView)getView().findViewById(R.id.InstructionsQuestions);


    TriviaHelper dbHelper = new TriviaHelper(getActivity());
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
    this.triviaIndex=0;
    }
        display(this.trivia.get(this.triviaIndex));
    }

    private void display(Trivia trivia)
    {
    TextView Question=(TextView)getView()findViewById(R.id.InstructionsQuestions);

    Question.setText(trivia.getQuestion());
    a1button.setText(trivia.getAnswer());
    a2button.setText(Trivia.getFalseAnswer());

    }

*/


}
