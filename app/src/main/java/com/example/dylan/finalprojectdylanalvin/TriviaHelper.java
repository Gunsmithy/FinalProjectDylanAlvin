package com.example.dylan.finalprojectdylanalvin;

//By Alvin

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TriviaHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION=1;
    public static final int DATABASE_VERSION2=1;
    public static final String DATABASE_FILENAME= "trivia.db";
    public static final String DATABASE_FILENAME2= "score.db";
    public static final String TABLE_NAME="Question and Answers";
    public static final String TABLE_NAME2="scores";

    public static final String CREATE_STATEMENT="CREATE TABLE " + TABLE_NAME+"("+" Questions"+"and Answers"+")";
    public static final String DROP_STATEMENT="DROP TABLE " + TABLE_NAME;
    public static final String CREATE_STATEMENT2="CREATE TABLE " + TABLE_NAME2+"("+" name "+"and scores"+")";
    public static final String DROP_STATEMENT2="DROP TABLE " + TABLE_NAME2;


    public TriviaHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(DROP_STATEMENT);
        database.execSQL(CREATE_STATEMENT);
    }
    public Trivia createTrivia(String Question, String Answer, String FalseAnswer) {
        // create the object
        Trivia trivia = new Trivia(Question, Answer, FalseAnswer);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("Question", trivia.getQuestion());
        values.put("Answer", trivia.getAnswer());
        values.put("FalseAnswer", trivia.getFalseAnswer());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        trivia.setId(id);

        return trivia;
    }

    public Trivia getTrivia(long id) {
        Trivia trivia = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the question and answer from the database
        String[] columns = new String[] { "question","answer", "false answer"};
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            String Question = cursor.getString(0);
            String Answer = cursor.getString(1);
            String FalseAnswer= cursor.getString(2);
            trivia = new Trivia(Question,Answer,FalseAnswer);
            trivia.setId(id);
        }

        Log.i("DatabaseAccess", "getTrivia(" + id + "):  Question: " + trivia);

        return trivia;
    }

    public ArrayList<Trivia> getAllTrivia() {
        ArrayList<Trivia> trivias = new ArrayList<Trivia>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the question and answer from the database
        String[] columns = new String[] { "_id", "Question", "Answser", "False Answer"};
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        cursor.moveToFirst();
        do {
            // collect the Question and answer data, and place it into 2 objects
            long id = Long.parseLong(cursor.getString(0));
            String Question = cursor.getString(1);
            String Answer = cursor.getString(2);
            String FalseAnswer=cursor.getString(3);
            Trivia trivia = new Trivia(Question,Answer,FalseAnswer);
            trivia.setId(id);

            // add the current Question to the list
            trivias.add(trivia);

            // advance to the next row in the results
            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("DatabaseAccess", "getAllQuestions():  num: " + trivias.size());

        return trivias;
    }
    public boolean updateTrivia(Trivia trivia) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("Question", trivia.getQuestion());
        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[]{"" + trivia.getId()});

        Log.i("DatabaseAccess", "updateQuestion(" + trivia + "):  numRowsAffected: " + numRowsAffected);

        // verify that the Question and Answer was updated successfully
        return (numRowsAffected == 1);
    }

}
