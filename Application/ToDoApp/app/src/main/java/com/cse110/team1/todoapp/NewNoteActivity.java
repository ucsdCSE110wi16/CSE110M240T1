package com.cse110.team1.todoapp;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

/**
 * Created by ameer on 3/3/16.
 */
public class NewNoteActivity extends AppCompatActivity{
    public static final String NOTE_DESCRIPTION = "note_description";
    public static final String NOTE_DETAILS = "note_details";

    // Called when user clicks save button
    public void saveNote (View view) {
        //EditText nameBox = (EditText) findViewById(R.id.task_field);
        //EditText detailBox = (EditText) findViewById(R.id.detail_field);

        // Create an intent to attach when this activity finishes.  The new task entry is still
        // added in this activity, but the intent remains in case MainActivity wants it.  Also,
        // this activity is launched with startActivityForResult anyway because MainActivity needs
        // to know when to refresh the ListView
        Intent result = new Intent();
        result.putExtra(NOTE_DESCRIPTION, nameBox.getText().toString());
        result.putExtra(NOTE_DETAILS, detailBox.getText().toString());
        setResult(RESULT_OK, result);


        // add new entry to table
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.insertTask(result.getExtras().getString(NewTaskActivity.TASK_DESCRIPTION),
//                result.getExtras().getString(NewTaskActivity.TASK_DETAILS),
//                null, null, mDay, mMonth, mYear, null,
//                result.getExtras().getInt(NewTaskActivity.TASK_PROGRESS),
//                isDone);
//        finish();
    }
}
