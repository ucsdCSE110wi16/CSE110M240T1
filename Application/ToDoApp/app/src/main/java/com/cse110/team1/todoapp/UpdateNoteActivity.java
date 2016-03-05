package com.cse110.team1.todoapp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

/**
 * Created by ameer on 3/4/16.
 */
public class UpdateNoteActivity extends AppCompatActivity{
    long noteId;
    private EditText nameBox;
    private EditText detailBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add Back Button to toolbar.  Parent activity is defined in app manifest
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        // Initialize values on screen
        nameBox = (EditText) findViewById(R.id.note_field);
        detailBox = (EditText) findViewById(R.id.detail_field);


        Intent intent = getIntent();
        nameBox.setText(intent.getStringExtra(DatabaseHelper.NOTEBOOK_COLUMN_TITLE));
        detailBox.setText(intent.getStringExtra(DatabaseHelper.NOTEBOOK_COLUMN_DESCRIPTION));

        noteId = intent.getIntExtra(DatabaseHelper.NOTEBOOK_COLUMN_ID, 2);
    }

    public void updateNote(View view) {

        nameBox = (EditText) findViewById(R.id.note_field);
        detailBox = (EditText) findViewById(R.id.detail_field);

        // update entry to table
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateNote(noteId, nameBox.getText().toString(), detailBox.getText().toString(),"");
        finish();
    }

    public void deleteNote(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deleteNote(noteId);
        finish();
    }
}
