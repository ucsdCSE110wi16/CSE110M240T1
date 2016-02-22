package com.cse110.team1.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.SeekBar;

public class UpdateTaskActivity extends AppCompatActivity {
    private EditText nameBox;
    private EditText detailBox;
    private SeekBar sbar;
    private int hello; //place holder to delete

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
    }

}
