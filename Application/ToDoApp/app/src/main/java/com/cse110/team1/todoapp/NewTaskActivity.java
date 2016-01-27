package com.cse110.team1.todoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
    }


    // Added Methods

    // Called when user clicks save button
    public void saveTask(View view) {
        EditText nameBox = (EditText) findViewById(R.id.task_field);
        EditText detailBox = (EditText) findViewById(R.id.detail_field);
        SeekBar sbar = (SeekBar) findViewById(R.id.progress_seek_bar);
        Intent result = new Intent();
        result.putExtra(TASK_DESCRIPTION, nameBox.getText().toString());
        result.putExtra(TASK_DETAILS, detailBox.getText().toString());
        result.putExtra(TASK_PROGRESS, sbar.getProgress());
        setResult(RESULT_OK, result);
        finish();
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
