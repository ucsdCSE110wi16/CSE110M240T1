package com.cse110.team1.todoapp;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static android.app.PendingIntent.getActivity;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
/**
 * Created by khaledahmad on 3/9/16.
 */
public class ClickAddTaskTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void clickOnAddButtonWhenInTaskTab(){
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null, false);
//        Intent myIntent = new Intent();
//        mActivityRule.launchActivity(myIntent);
        onView(withId(R.id.action_new_task)).perform(click());

        onView(withId(R.id.task_field)).check(matches(withHint("Task Name")));
        onView(withId(R.id.detail_field)).check(matches(withHint("Enter Task Details Here")));
        onView(withId(R.id.progessBar)).check(matches(withText("% Completed")));




//        UpdateTaskActivity nextActivity = (UpdateTaskActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);
//        Log.d("Hello", "");
//        assertNotNull(nextActivity);
//        nextActivity.finish();

    }
    @Test
    public void clickOnAddButtonWhenInNoteTab(){
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null, false);
        onView(withId(R.id.tabs)).perform(click());
        onView(withId(R.id.action_new_task)).perform(click());

        onView(withId(R.id.note_field)).check(matches(withHint("Note Name")));
        onView(withId(R.id.detail_field)).check(matches(withHint("Enter Note Details Here")));

    }

    @Test
    public void clickOnTask(){
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null, false);
        onView(withId(R.id.task_list_view_frag)).perform(click());

        onView(withId(R.id.progessBar)).check(matches(withText("% Completed")));
    }

    @Test
    public void clickOnNote(){
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null, false);
        onView(withId(R.id.tabs)).perform(click());
        onView(withId(R.id.note_list_view_frag)).perform(click());


    }


}
