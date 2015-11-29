package com.example.dylan.finalprojectdylanalvin;

/**
 * Created by 100484342 on 23/11/2015.
 */
public class Trivia {
    private long id;
    private String question;
    private String answer;
    //Table for questions and answers
    public Trivia(String Question, String Answer) {
        this.id = -1; // must be updated after the object is created
        this.question = Question;
        this.answer=Answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String Question){this.question=Question; }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String Answer){this.answer=Answer; }

    public String toString() {
        return question;
    }


}
