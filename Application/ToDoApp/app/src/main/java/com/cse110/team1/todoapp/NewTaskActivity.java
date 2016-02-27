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

/*
 * NewTaskActivity class:
 * This activity is responsible for displaying and retrieving input from the user in order
 * to gather the data for a new Task entry in the database.  This class does not add the data to
 * the database itself.  Rather, it will pass the data back to the main activity through the
 * main activity's onActivityResult(...) method.
 * -- Darren Eck
 */

public class NewTaskActivity extends AppCompatActivity {
    public static final String TASK_DESCRIPTION = "task_description";
    public static final String TASK_DETAILS = "task_details";
    public static final String TASK_PROGRESS = "task_progress";
    public static final String TASK_YEAR = "task_year";
    public static final String TASK_MONTH = "task_month";
    public static final String TASK_DAY = "task_day";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    // for setting the due date
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_task_toolbar);
        setSupportActionBar(toolbar);

        // Add Back Button to toolbar.  Parent activity is defined in app manifest
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        mYear = 0;
        mMonth = 0;
        mDay = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    // Added Methods

    // Called when user clicks save button
    public void saveTask(View view) {
        EditText nameBox = (EditText) findViewById(R.id.task_field);
        EditText detailBox = (EditText) findViewById(R.id.detail_field);
        SeekBar sbar = (SeekBar) findViewById(R.id.progress_seek_bar);

        // Create an intent to attach when this activity finishes.  The new task entry is still
        // added in this activity, but the intent remains in case MainActivity wants it.  Also,
        // this activity is launched with startActivityForResult anyway because MainActivity needs
        // to know when to refresh the ListView
        Intent result = new Intent();
        result.putExtra(TASK_DESCRIPTION, nameBox.getText().toString());
        result.putExtra(TASK_DETAILS, detailBox.getText().toString());
        result.putExtra(TASK_PROGRESS, sbar.getProgress());
        setResult(RESULT_OK, result);

        boolean isDone = false;
        if (sbar.getProgress() >= 100) {
            isDone = true;
        }
        // add new entry to table
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.insertTask(result.getExtras().getString(NewTaskActivity.TASK_DESCRIPTION),
                result.getExtras().getString(NewTaskActivity.TASK_DETAILS),
                null, null, mDay, mMonth, mYear, null,
                result.getExtras().getInt(NewTaskActivity.TASK_PROGRESS),
                isDone);
        finish();
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewTask Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.cse110.team1.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewTask Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.cse110.team1.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
