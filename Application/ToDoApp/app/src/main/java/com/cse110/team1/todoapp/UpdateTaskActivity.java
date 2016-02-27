package com.cse110.team1.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;

public class UpdateTaskActivity extends AppCompatActivity {
    private EditText nameBox;
    private EditText detailBox;
    private SeekBar sbar;
    private TextView dateText;
    private long taskId;

    private int mDay, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add Back Button to toolbar.  Parent activity is defined in app manifest
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        // Initialize values on screen
        nameBox = (EditText) findViewById(R.id.task_field);
        detailBox = (EditText) findViewById(R.id.detail_field);
        sbar = (SeekBar) findViewById(R.id.progress_seek_bar);
        dateText = (TextView) findViewById(R.id.due_date_view);


        Intent intent = getIntent();
        nameBox.setText(intent.getStringExtra(DatabaseHelper.TASK_COLUMN_NAME));
        detailBox.setText(intent.getStringExtra(DatabaseHelper.TASK_COLUMN_DETAILS));
        sbar.setProgress(intent.getIntExtra(DatabaseHelper.TASK_COLUMN_PERCENT, 0));

        mYear = intent.getIntExtra(DatabaseHelper.TASK_COLUMN_DUE_YEAR, 0);
        mMonth = intent.getIntExtra(DatabaseHelper.TASK_COLUMN_DUE_MONTH, 0);
        mDay = intent.getIntExtra(DatabaseHelper.TASK_COLUMN_DUE_DAY, 0);
        if (mYear != 0) {
            String date = String.format(getResources().getString((R.string.date_format)),
                    mMonth, mDay, mYear);
            dateText.setText(date);
        }
        taskId = intent.getIntExtra(DatabaseHelper.TASK_COLUMN_ID, 2);
    }

    // promptDate: Called when user clicks Set Due Date button
    // DatePickerDialog.OnDateSetListener determines behavior when date picker fragment is finished
    DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            TextView tv = (TextView) findViewById(R.id.due_date_view);
            String date = String.format(getResources().getString((R.string.date_format)),
                    mMonth, mDay, mYear);
            tv.setText(date);
        }
    };
    public void promptDate(View view) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, odsl, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }


    // Called when user clicks save button
    public void updateTask(View view) {

        nameBox = (EditText) findViewById(R.id.task_field);
        detailBox = (EditText) findViewById(R.id.detail_field);
        sbar = (SeekBar) findViewById(R.id.progress_seek_bar);

        boolean isDone = false;
        if (sbar.getProgress() >= 100) {
            isDone = true;
        }

        // update entry to table
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateTask(taskId, nameBox.getText().toString(), detailBox.getText().toString(),
                mDay, mMonth, mYear, sbar.getProgress(), isDone);
        finish();
    }

    // Called When User Clicks Delete Button

    public void deleteTask(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deleteTask(taskId);
        finish();
    }

}
