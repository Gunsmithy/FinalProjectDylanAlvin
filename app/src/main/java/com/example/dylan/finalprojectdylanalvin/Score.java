package com.example.dylan.finalprojectdylanalvin;

//By Alvin

public class Score {
    private long id;
    private String name;
    private int score;
    //Table for score
    public Score(String Name, int Score)
    {
        this.id=-1;
        this.name=Name;
        this.score=Score;
    }

    public String getName(){
        return name;
    }
    public void setName(String Name)
    {
        this.name=Name;
    }
    public int getScore()
    {
        return score;
    }
    public void setScpre(int Score)
    {
        this.score=Score;
    }
}
