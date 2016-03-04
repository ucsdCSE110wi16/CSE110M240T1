package com.cse110.team1.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    public static final String TASK_COLUMN_DUE_DAY = "due_day";
    public static final String TASK_COLUMN_DUE_MONTH = "due_month";
    public static final String TASK_COLUMN_DUE_YEAR = "due_year";
    public static final String TASK_COLUMN_COMPLETED_TIME = "completed";
    public static final String TASK_COLUMN_PERCENT = "percent";
    public static final String TASK_COLUMN_DONE = "done";

    public static final String NOTEBOOK_TABLE_NAME = "notes";
    public static final String NOTEBOOK_COLUMN_ID = "_id";
    public static final String NOTEBOOK_COLUMN_DESCRIPTION = "description";
    public static final String NOTEBOOK_COLUMN_TITLE = "title";
    public static final String NOTEBOOK_COLUMN_CREATED_TIME = "dateCreated";


    //TODO: Add table for Notebook Notes

    private static final int DATABASE_VERSION_NUMBER = 3;
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Task table
        db.execSQL("create table " + TASK_TABLE_NAME + " (" +
                        TASK_COLUMN_ID + " integer primary key autoincrement not null, " +
                        TASK_COLUMN_NAME + " text, " +
                        TASK_COLUMN_DETAILS + " text, " +
                        TASK_COLUMN_CREATED_TIME + " text, " +
                        TASK_COLUMN_DUE_TIME + " text, " +
                        TASK_COLUMN_DUE_DAY + " integer, " +
                        TASK_COLUMN_DUE_MONTH + " integer, " +
                        TASK_COLUMN_DUE_YEAR + " integer, " +
                        TASK_COLUMN_COMPLETED_TIME + " text, " +
                        TASK_COLUMN_PERCENT + " integer, " +
                        TASK_COLUMN_DONE + " boolean)"
        );
        //Notebook table
         db.execSQL("create table " + NOTEBOOK_TABLE_NAME + " (" +
                         NOTEBOOK_COLUMN_ID + " integer primary key  autoincrement not null, " +
                         NOTEBOOK_COLUMN_DESCRIPTION + "text, " +
                         NOTEBOOK_COLUMN_TITLE + " text, " +
                         NOTEBOOK_COLUMN_CREATED_TIME + " text)"
         );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NOTEBOOK_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertNote(String title, String desc, String created){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTEBOOK_COLUMN_TITLE, title);
        contentValues.put(NOTEBOOK_COLUMN_DESCRIPTION, desc);
        contentValues.put(NOTEBOOK_COLUMN_CREATED_TIME, created);

        long returnid = db.insert(NOTEBOOK_TABLE_NAME, null, contentValues);
        boolean isSuccessful = true;
        if (returnid == -1)
            isSuccessful = false;
        return isSuccessful;

    }

    public boolean updateNote(long noteId, String title, String desc, String created){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTEBOOK_COLUMN_TITLE, title);
        contentValues.put(NOTEBOOK_COLUMN_DESCRIPTION, desc);
        contentValues.put(NOTEBOOK_COLUMN_CREATED_TIME, created);
        long returnId = db.update(NOTEBOOK_TABLE_NAME, contentValues, NOTEBOOK_COLUMN_ID + " = " + noteId, null);
        boolean isSuccessful = true;
        if(returnId == -1)
            isSuccessful = false;
        return isSuccessful;
    }

    public boolean deleteNote(long noteID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTEBOOK_TABLE_NAME, NOTEBOOK_COLUMN_ID + "=" + noteID, null) > 0;
    }
    /*
     * Inserts new task into task table. At least one entry must be non-null.
     * Returns true if insert was successful, false otherwise.
     */
    public boolean insertTask(String desc, String details, String created, String due, int dueDay,
                              int dueMonth, int dueYear, String completed, int percent,
                              boolean done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_NAME, desc);
        contentValues.put(TASK_COLUMN_DETAILS, details);
        contentValues.put(TASK_COLUMN_CREATED_TIME, created);
        contentValues.put(TASK_COLUMN_DUE_TIME, due);
        contentValues.put(TASK_COLUMN_DUE_DAY, dueDay);
        contentValues.put(TASK_COLUMN_DUE_MONTH, dueMonth);
        contentValues.put(TASK_COLUMN_DUE_YEAR, dueYear);
        contentValues.put(TASK_COLUMN_COMPLETED_TIME, completed);
        contentValues.put(TASK_COLUMN_PERCENT, percent);
        contentValues.put(TASK_COLUMN_DONE, done);
        long returnid = db.insert(TASK_TABLE_NAME, null, contentValues);
        boolean isSuccessful = true;
        if (returnid == -1)
            isSuccessful = false;
        return isSuccessful;
    }

    /* Update Tasks */
    public boolean updateTask(long taskId, String name, String details, int day, int month,
                              int year, int percent, boolean done){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_NAME, name);
        contentValues.put(TASK_COLUMN_DETAILS, details);
        contentValues.put(TASK_COLUMN_DUE_DAY, day);
        contentValues.put(TASK_COLUMN_DUE_MONTH, month);
        contentValues.put(TASK_COLUMN_DUE_YEAR, year);
        contentValues.put(TASK_COLUMN_PERCENT, percent);
        contentValues.put(TASK_COLUMN_DONE, done);
        long returnId = db.update(TASK_TABLE_NAME, contentValues, TASK_COLUMN_ID + " = " + taskId, null);
        boolean isSuccessful = true;

        if (returnId == -1) {
            isSuccessful = false;
        }
        return isSuccessful;
    }

//    Delete Tasks
    public boolean deleteTask(long taskId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TASK_TABLE_NAME, TASK_COLUMN_ID + "=" + taskId, null) > 0;
    }

    //Count Rows in Database. Wayne Combs
    //Returns integer count of all tasks present.
    public int getTaskCount() {
        String countQuery = "select * from " + TASK_TABLE_NAME;
        SQLiteDatabase dbHelper = this.getReadableDatabase();
        Cursor cursor = dbHelper.rawQuery(countQuery, null);
        if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
            cursor.close();
            return cursor.getCount();
        } else {
            cursor.close();
            return 0;
        }
    }

    //Count how many tasks are completed. Wayne Combs
    //Loops through database and counts how many
    //true instances are found under the done column
    public int getDoneCount(){
        int doneCount = 0;
        String selectQuery = "select * from " + TASK_TABLE_NAME;
        SQLiteDatabase dbHelper = this.getReadableDatabase();
        Cursor cursor = dbHelper.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                boolean value = (cursor.getInt(10) == 1);
                if(value) doneCount++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        return doneCount;
    }

    //Count how many tasks are overdue. Wayne Combs
    //Loops through database and counts how many
    //tasks are overdue and return count of overdue instances.
    public int getOverdueCount(){
        DateFormat df = new SimpleDateFormat("MMddYYYYHHmm");
        String date = df.format(Calendar.getInstance());
        int dateInt = Integer.parseInt(date);
        int OverDueCount = 0;
        String selectQuery = "select * from " + TASK_TABLE_NAME;
        SQLiteDatabase dbHelper = this.getReadableDatabase();
        Cursor cursor = dbHelper.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                String value = (cursor.getString(Integer.parseInt(TASK_COLUMN_DUE_MONTH)));
                value = value + (cursor.getString(Integer.parseInt(TASK_COLUMN_DUE_DAY)));
                value = value + (cursor.getString(Integer.parseInt(TASK_COLUMN_DUE_YEAR)));
                value = value + (cursor.getString(Integer.parseInt(TASK_COLUMN_DUE_TIME)));
                if(dateInt > (Integer.parseInt(value))) OverDueCount++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        return OverDueCount;
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
        while(!res.isAfterLast()){
            taskList.add(res.getString(res.getColumnIndex(TASK_COLUMN_NAME)));
            res.moveToNext();
        }

        res.close();
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
        return db.query(TASK_TABLE_NAME, columns,
                null, null, null, null, null);
    }

    /*
     * Returns cursor object containing all information from all columns/rows, ordered by increasing
     * progress (full on bottom, no progress on top)
     */
    public Cursor fetchAllTasksByProgress() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {TASK_COLUMN_ID, TASK_COLUMN_NAME, TASK_COLUMN_DETAILS,
                TASK_COLUMN_CREATED_TIME, TASK_COLUMN_DUE_TIME, TASK_COLUMN_DUE_DAY,
                TASK_COLUMN_DUE_MONTH, TASK_COLUMN_DUE_YEAR, TASK_COLUMN_COMPLETED_TIME,
                TASK_COLUMN_PERCENT, TASK_COLUMN_DONE};
        String orderBy  = TASK_COLUMN_PERCENT;
        return db.query(TASK_TABLE_NAME, columns,
                null, null, null, null, orderBy);
    }

    /*
     * Returns cursor object containing all information, orgainzed by year, month, day, then
     * progress
     */
    public Cursor fetchAllTasksByDateThenProgress() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {TASK_COLUMN_ID, TASK_COLUMN_NAME, TASK_COLUMN_DETAILS,
                TASK_COLUMN_CREATED_TIME, TASK_COLUMN_DUE_TIME, TASK_COLUMN_DUE_DAY,
                TASK_COLUMN_DUE_MONTH, TASK_COLUMN_DUE_YEAR, TASK_COLUMN_COMPLETED_TIME,
                TASK_COLUMN_PERCENT, TASK_COLUMN_DONE};
        String orderBy  = TASK_COLUMN_DONE + ", " +
                          TASK_COLUMN_DUE_YEAR + ", " +
                          TASK_COLUMN_DUE_MONTH + ", " +
                          TASK_COLUMN_DUE_DAY + ", " +
                          TASK_COLUMN_PERCENT;
        return db.query(TASK_TABLE_NAME, columns,
                null, null, null, null, orderBy);
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
                        TASK_COLUMN_DUE_DAY + " integer, " +
                        TASK_COLUMN_DUE_MONTH + " integer, " +
                        TASK_COLUMN_DUE_YEAR + " integer, " +
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
                TASK_COLUMN_CREATED_TIME, TASK_COLUMN_DUE_TIME, TASK_COLUMN_DUE_DAY,
                TASK_COLUMN_DUE_MONTH, TASK_COLUMN_DUE_YEAR, TASK_COLUMN_COMPLETED_TIME,
                TASK_COLUMN_PERCENT, TASK_COLUMN_DONE};
        String[] args = {""+id};
        return db.query(TASK_TABLE_NAME, columns, TASK_COLUMN_ID + " = ?", args,
                null, null, null);
    }
}
