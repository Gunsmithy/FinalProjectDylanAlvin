package com.example.dylan.finalprojectdylanalvin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 100484342 on 23/11/2015.
 */
public class TriviaHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_FILENAME= "trivia.db";
    public static final String TABLE_NAME="Trivia Results";

    public static final String CREATE_STATEMENT="CREATE TABLE " + TABLE_NAME+"("+" name created"+")";
    public static final String DROP_STATEMENT="DROP TABLE " + TABLE_NAME;


    public TriviaHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // the implementation below is adequate for the first version
        // however, if we change our table at all, we'd need to execute code to move the data
        // to the new table structure, then delete the old tables (renaming the new ones)

        // the current version destroys all existing data
        database.execSQL(DROP_STATEMENT);
        database.execSQL(CREATE_STATEMENT);
    }
    public Trivia createTrivia(String Name) {
        // create the object
        Trivia trivia = new Trivia(Name);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("Name", trivia.getNameCreated());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        trivia.setId(id);

        return trivia;
    }

    public Trivia getTrivia(long id) {
        Trivia trivia = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the name from the database
        String[] columns = new String[] { "Name Created"};
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            String NameCreated = cursor.getString(0);
            trivia = new Trivia(NameCreated);
            trivia.setId(id);
        }

        Log.i("DatabaseAccess", "getTrivia(" + id + "):  name: " + trivia);

        return trivia;
    }

    public ArrayList<Trivia> getAllTrivia() {
        ArrayList<Trivia> trivias = new ArrayList<Trivia>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the name from the database
        String[] columns = new String[] { "_id", "Name"};
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        cursor.moveToFirst();
        do {
            // collect the Name data, and place it into a Name object
            long id = Long.parseLong(cursor.getString(0));
            String NameCreated = cursor.getString(1);
            Trivia trivia = new Trivia(NameCreated);
            trivia.setId(id);

            // add the current Name to the list
            trivias.add(trivia);

            // advance to the next row in the results
            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("DatabaseAccess", "getAllNamess():  num: " + trivias.size());

        return trivias;
    }
    public boolean updateTrivia(Trivia trivia) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("firstName", trivia.getNameCreated());
        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[]{"" + trivia.getId()});

        Log.i("DatabaseAccess", "updateName(" + trivia + "):  numRowsAffected: " + numRowsAffected);

        // verify that the name was updated successfully
        return (numRowsAffected == 1);
    }
    public boolean deleteTrivia(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the name
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[] { "" + id });

        Log.i("DatabaseAccess", "deleteName(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the name was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllTrivias() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the name
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllNames():  numRowsAffected: " + numRowsAffected);
    }
}
