package com.cse110.team1.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class UpdateTaskActivity extends AppCompatActivity {
    private EditText nameBox;
    private EditText detailBox;
    private SeekBar sbar;
    private long taskId;

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

        Intent intent = getIntent();
        nameBox.setText(intent.getStringExtra(DatabaseHelper.TASK_COLUMN_NAME));
        detailBox.setText(intent.getStringExtra(DatabaseHelper.TASK_COLUMN_DETAILS));
        sbar.setProgress(intent.getIntExtra(DatabaseHelper.TASK_COLUMN_PERCENT, 0));
        taskId = intent.getIntExtra(DatabaseHelper.TASK_COLUMN_ID,0);
    }

    // Called when user clicks save button
    public void updateTask(View view) {

        nameBox = (EditText) findViewById(R.id.task_field);
        detailBox = (EditText) findViewById(R.id.detail_field);
        sbar = (SeekBar) findViewById(R.id.progress_seek_bar);



        // update entry to table
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateTask(taskId, nameBox.getText().toString(), detailBox.getText().toString(), sbar.getProgress());
        Log.d("HELLO", nameBox.getText().toString());

        finish();
    }

}
