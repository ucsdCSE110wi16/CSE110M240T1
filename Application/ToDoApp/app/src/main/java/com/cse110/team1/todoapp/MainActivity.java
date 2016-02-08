package com.cse110.team1.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity{

    public static final int CREATE_TASK_REQUEST = 1;

    private DatabaseHelper dbHelper;

    ViewPager pager;
    ViewPageAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Tasks","Notes", "Performace"};
    int Numboftabs =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPageAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);



        // Set up database
        dbHelper = new DatabaseHelper(this);
//        dbHelper.remakeTaskTable();
//        populateTaskList();
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
                // TODO: we should maybe not hard code the getItem index
                TasksFragment tf = (TasksFragment) adapter.getItem(1);
                tf.populateTaskList();
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
                // TODO: we should maybe not hard code the getItem index
                TasksFragment tf = (TasksFragment) adapter.getItem(1);
                tf.populateTaskList();
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
}
