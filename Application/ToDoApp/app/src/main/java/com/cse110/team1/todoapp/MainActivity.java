package com.cse110.team1.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    public static final int CREATE_TASK_REQUEST = 1;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        // Set up database
        dbHelper = new DatabaseHelper(this);
        dbHelper.remakeTaskTable();
        populateTaskList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // Method to handle menu selections on the app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.action_settings:
                // TODO: Implement actual settings and bind to action
                return true;

            case R.id.action_new_task:
                createTask();
                return true;

            case R.id.action_delete_all_tasks:
                dbHelper.remakeTaskTable();
                populateTaskList();
                return true;

            default:
                // None of our actions were initiated, refer to super class
                return super.onOptionsItemSelected(item);
        }
    }

    // Method to handle data returned from a finished activity started by this activity
    @Override
    protected void onActivityResult(int resultCode, int requestCode, Intent data) {
        if (resultCode == CREATE_TASK_REQUEST) {

            if (requestCode == RESULT_OK) {
                // update list of tasks
                populateTaskList();
            }
        }
    }



    /*
     * Create Task method, used to initiate the activity to enter details for a new task.
     *
     * Oddly enough, in the onActivityResult(..) method,  resultCode holds the request code,
     * and requestCode holds the status of the result, which seems to be opposite of what all
     * documentation I could find said... but hey, after inspecting the values manually, they
     * looked reversed, so I swapped them and everything seems to work fine now. --Darren
     */
    public void createTask() {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(intent, CREATE_TASK_REQUEST);
    }


    private void populateTaskList() {
        Cursor cursor = dbHelper.fetchAllTasks();

        // columns used to populate list view elements
        String[] columns = {DatabaseHelper.TASK_COLUMN_NAME,
                DatabaseHelper.TASK_COLUMN_DETAILS,
                DatabaseHelper.TASK_COLUMN_PERCENT};

        // xml components to bind data to
        int[] components = {R.id.description_view,
                R.id.detail_view,
                R.id.task_progress_bar};

        // create cursor adapter to populate view elements and pass to the list view
        SimpleCursorAdapter cadapter = new SimpleCursorAdapter(this, R.layout.task_list_info,
                cursor, columns, components, 0);
        // assign custom ViewBinder to handle binding to non TextBox views.
        cadapter.setViewBinder(new TaskViewBinder());

        ListView lview = (ListView) findViewById(R.id.task_list_view);
        lview.setAdapter(cadapter);
    }
}
