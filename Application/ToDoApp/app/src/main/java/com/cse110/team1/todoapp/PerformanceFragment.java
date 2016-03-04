package com.cse110.team1.todoapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cse110.team1.todoapp.MainActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by khaledahmad on 2/6/16.
 */
public class PerformanceFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private int taskCount = 0;
    private int doneCount = 0;
    private int overdueCount = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.performace_fragment,container,false);


        //Working on database integration with graph
        dbHelper = new DatabaseHelper(getActivity());
        taskCount = dbHelper.getTaskCount();
        doneCount = dbHelper.getDoneCount();
        overdueCount = dbHelper.getOverdueCount();
        //Added graph to performance tab. Wayne Combs 2.21.16
        int[] taskPercentage = new int[taskCount];
        Cursor cursor = dbHelper.fetchAllTasksByDateThenProgress();
        int index = 0;
        if(cursor.moveToFirst()){
            do{
                taskPercentage[index] = cursor.getInt(9);
                index++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        GraphView line_graph = (GraphView) v. findViewById(R.id.graph);
        LineGraphSeries<DataPoint> line_series = new LineGraphSeries<DataPoint>(generateData(taskPercentage));
        line_graph.addSeries(line_series);
        /*LineGraphSeries<DataPoint> line_series =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, taskCount),
                        new DataPoint(1, taskCount),
                        new DataPoint(2, taskCount),
                        new DataPoint(3, doneCount),
                        new DataPoint(4, doneCount)
                });
        line_graph.addSeries(line_series);
        */
        return v;
    }

    private DataPoint[] generateData(int[] tasksPercentage){
        DataPoint[] values = new DataPoint[taskCount];
        for(int i=0; i<taskCount; i++){
            DataPoint v = new DataPoint (i+1, tasksPercentage[i]);
            values[i] = v;
        }
        return values;
    }
}
