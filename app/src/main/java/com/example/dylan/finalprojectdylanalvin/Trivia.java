package com.example.dylan.finalprojectdylanalvin;

/**
 * Created by 100484342 on 23/11/2015.
 */
public class Trivia {
    private long id;
    private String Name;

    public Trivia(String NameCreated) {
        this.id = -1; // must be updated after the object is created
        this.Name = NameCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameCreated() {
        return Name;
    }

    public void setNameCreated(String NameCreated) {
        this.Name = NameCreated;
    }

    public String toString() {
        return Name;
    }
}
