package com.cse110.team1.todoapp;

import android.database.Cursor;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

/**
 * Created by darreneck on 1/26/16.
 *
 * This class implements SimpleCursorAdapter.ViewBinder, which is a nested class within
 * SimpleCursorAdapter.  The ListView on the main activity that lists all tasks in the database
 * is populated by a SimpleCursorAdapter.  However, it is indeed very "simple", as it only knows
 * how to populate TextViews (and I think ImageViews, maybe?).  So, to get it to populate the
 * progress bars on the ListView, we are implementing our own custom ViewBinder to handle it.
 *
 */
public class TaskViewBinder implements SimpleCursorAdapter.ViewBinder {


    /*
     * This is the (only) abstract method in ViewBinder that must be implemented.  If it returns
     * true, then that means the value was handled by this method.  If it returns false, then it
     * is signifying SimpleCursorAdapter to handle the binding on its own.
     *
     * In other words, return false if the View view is a TextView, or bind data and return true
     * if it is not.
     *
     * The Cursor cursor should also be in the correct position already.
     */
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        boolean returnValue = false;

        // Check if current cursor column is holding an integer (for the progress bar)
        if (cursor.getType(columnIndex) == Cursor.FIELD_TYPE_INTEGER) {
            int value = cursor.getInt(columnIndex);
            // the view must be a progress bar, since that's the only integer value in the table
            // that this ViewBinder is used for.  This will have to change if more integers are
            // added to the task table.
            ((ProgressBar) view).setProgress(value);

            returnValue = true;
        }
        return returnValue;
    }
}
