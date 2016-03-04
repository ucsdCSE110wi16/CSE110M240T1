package com.cse110.team1.todoapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

public class ViewPageAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int position = 0;

    // This will store the fragments for later access
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPageAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            TasksFragment taskFragment = new TasksFragment();
            this.position = 0;
            return taskFragment;
        }else if(position == 1) {
            NotesFragment notesFrag = new NotesFragment();
            this.position = 1;
            return notesFrag;

        }else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            PerformanceFragment performanceFrag = new PerformanceFragment();
            this.position = 2;
            return performanceFrag;
        }


    }

    public int getPosition(){
        return position;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}


