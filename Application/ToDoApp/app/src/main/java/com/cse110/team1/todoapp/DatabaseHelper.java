package com.cse110.team1.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by team1 on 1/25/16.
 *
 * The purpose of this class is to encapsulate and separate the basic Database operations from the
 * activity, in order to keep coupling loose.  In this manner, it should be possible to change
 * database solutions without having to dig into the main activity class.
 *
 * As it is now, this class implements SQLite via SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    // Strings are not defined in the R.string resource because I wanted to make them final, while
    // still able to be used in the public constructor.  They are also not a part of any UI
    // elements.  This can be easily changed later, if necessary. --Darren
    public static final String DATABASE_NAME = "TaskDatabase.db";
    public static final String TASK_TABLE_NAME = "tasks";
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_COLUMN_NAME = "description";
    public static final String TASK_COLUMN_DETAILS = "details";
    public static final String TASK_COLUMN_CREATED_TIME = "created";
    public static final String TASK_COLUMN_DUE_TIME = "due";
    public static final String TASK_COLUMN_COMPLETED_TIME = "completed";
    public static final String TASK_COLUMN_PERCENT = "percent";
    public static final String TASK_COLUMN_DONE = "done";

    public static final String NOTEBOOK_TABLE_NAME = "notes";
    public static final String NOTEBOOK_COLUMN_ID = "_id";
    public static final String NOTEBOOK_COLUMN_DESCRIPTION = "description";
    public static final String NOTEBOOK_COLUMN_TITLE = "title";
    public static final String NOTEBOOK_COLUMN_CREATED_TIME = "dateCreated";


    //TODO: Add table for Notebook Notes


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Task table
        db.execSQL("create table " + TASK_TABLE_NAME + " (" +
                        TASK_COLUMN_ID + " integer primary key, " +
                        TASK_COLUMN_NAME + " text, " +
                        TASK_COLUMN_DETAILS + "text, " +
                        TASK_COLUMN_CREATED_TIME + " text, " +
                        TASK_COLUMN_DUE_TIME + " text, " +
                        TASK_COLUMN_COMPLETED_TIME + " text, " +
                        TASK_COLUMN_PERCENT + "integer, " +
                        TASK_COLUMN_DONE + " boolean)"
        );
        //Notebook table
        // db.execSQL("create table " + TASK_TABLE_NAME + " (" +
        //                 TASK_COLUMN_ID + " integer primary key, " +
        //                 TASK_COLUMN_NAME + " text, " +
        //                 TASK_COLUMN_DETAILS + "text, " +
        //                 TASK_COLUMN_CREATED_TIME + " text, " +
        //                 TASK_COLUMN_DUE_TIME + " text, " +
        //                 TASK_COLUMN_COMPLETED_TIME + " text, " +
        //                 TASK_COLUMN_PERCENT + "integer, " +
        //                 TASK_COLUMN_DONE + " boolean)"
        // );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
        onCreate(db);
    }


    /*
     * Inserts new task into task table. At least one entry must be non-null.
     * Returns true if insert was successful, false otherwise.
     */
    public boolean insertTask(String desc, String details, String created, String due,
                              String completed, int percent, boolean done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_NAME, desc);
        contentValues.put(TASK_COLUMN_DETAILS, details);
        contentValues.put(TASK_COLUMN_CREATED_TIME, created);
        contentValues.put(TASK_COLUMN_DUE_TIME, due);
        contentValues.put(TASK_COLUMN_COMPLETED_TIME, completed);
        contentValues.put(TASK_COLUMN_PERCENT, percent);
        contentValues.put(TASK_COLUMN_DONE, done);
        long returnid = db.insert(TASK_TABLE_NAME, null, contentValues);
        boolean isSuccessful = true;
        if (returnid == -1)
            isSuccessful = false;
        return isSuccessful;
    }


    /*
     * Returns an ArrayList<String> of all descriptions of all tasks in the table.
     * If table is empty, the ArrayList will be as well (obviously).
     */
    public ArrayList<String> getAllTaskDescriptions(){
        ArrayList<String> taskList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery("select * from "+TASK_TABLE_NAME, null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            taskList.add(res.getString(res.getColumnIndex(TASK_COLUMN_NAME)));
            res.moveToNext();
        }
        return taskList;
    }

    /*
     * Returns cursor object containing all information from all columns/rows.
     */
    public Cursor fetchAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {TASK_COLUMN_ID, TASK_COLUMN_NAME, TASK_COLUMN_DETAILS,
                TASK_COLUMN_CREATED_TIME, TASK_COLUMN_DUE_TIME, TASK_COLUMN_COMPLETED_TIME,
                TASK_COLUMN_PERCENT, TASK_COLUMN_DONE};
        Cursor cursor = db.query(TASK_TABLE_NAME, columns,
                null, null, null, null, null);
        return cursor;
    }

    /*
     * Remake the table of tasks, in order to update.  Effectively deletes all entries.
     */
    public void remakeTaskTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE "+TASK_TABLE_NAME);
        db.execSQL("create table " + TASK_TABLE_NAME + " (" +
                        TASK_COLUMN_ID + " integer primary key, " +
                        TASK_COLUMN_NAME + " text, " +
                        TASK_COLUMN_DETAILS + " text, " +
                        TASK_COLUMN_CREATED_TIME + " text, " +
                        TASK_COLUMN_DUE_TIME + " text, " +
                        TASK_COLUMN_COMPLETED_TIME + " text, " +
                        TASK_COLUMN_PERCENT + " integer, " +
                        TASK_COLUMN_DONE + " boolean)"
        );
    }

    /*
     * Get a single row from the task table
     */
    public Cursor getTaskById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {TASK_COLUMN_ID, TASK_COLUMN_NAME, TASK_COLUMN_DETAILS,
                TASK_COLUMN_CREATED_TIME, TASK_COLUMN_DUE_TIME, TASK_COLUMN_COMPLETED_TIME,
                TASK_COLUMN_PERCENT, TASK_COLUMN_DONE};
        String[] args = {""+id};
        Cursor cursor = db.query(TASK_TABLE_NAME, columns, TASK_COLUMN_ID + " = ?", args,
                null, null, null);
        return cursor;
    }
}
