package com.cse110.team1.todoapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by khaledahmad on 2/6/16.
 */
public class NotesFragment extends Fragment {
    private DatabaseHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.notes_fragment,container,false);
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Access Database
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Fill list view
        populateNoteList();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateNoteList();
    }

    public void populateNoteList() {
   //     Cursor cursor = dbHelper.fetchAllTasks();
//        Cursor cursor = dbHelper.fetchAllTasksByProgress();
        Cursor cursor = dbHelper.fetchAllNotes();
        //Cursor cursor1 = dbHelper.getNoteById(1);

        // columns used to populate list view elements
        String[] columns = {DatabaseHelper.NOTEBOOK_COLUMN_TITLE,
                DatabaseHelper.NOTEBOOK_COLUMN_DESCRIPTION};

        // xml components to bind data to
        int[] components = {R.id.description_view,
                R.id.detail_view};

        // create cursor adapter to populate view elements and pass to the list view
        SimpleCursorAdapter cadapter = new SimpleCursorAdapter(getActivity(), R.layout.note_list_info,
                cursor, columns, components, 0);
        // assign custom ViewBinder to handle binding to non TextBox views.
        cadapter.setViewBinder(new TaskViewBinder());

        ListView lview = (ListView) getActivity().findViewById(R.id.note_list_view_frag);
        lview.setAdapter(cadapter);


        // Set OnItemClickListener for newly populated lview
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                onTaskSelected(id);
            }
        });


        // Get rid of text box in background if list gets populated, or re-add if list gets deleted
        TextView tv = (TextView) getActivity().findViewById(R.id.note_fragment_text_view);

        if (cursor.getCount() > 0)
            tv.setVisibility(View.INVISIBLE);
        else
            tv.setVisibility(View.VISIBLE);
    }

    public void onTaskSelected(long id) {
        String name = "";
        Cursor cursor = dbHelper.getNoteById(id);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTEBOOK_COLUMN_TITLE));
                String details = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTEBOOK_COLUMN_DESCRIPTION));

                Intent intent = new Intent(getActivity(), UpdateNoteActivity.class);
                intent.putExtra(DatabaseHelper.NOTEBOOK_COLUMN_ID, Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTEBOOK_COLUMN_ID))));
                intent.putExtra(DatabaseHelper.NOTEBOOK_COLUMN_TITLE, name);
                intent.putExtra(DatabaseHelper.NOTEBOOK_COLUMN_DESCRIPTION, details);

                getActivity().startActivity(intent);
            }
        }

    }
}

