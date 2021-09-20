package com.example.farmmanager.ui.bottomNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmmanager.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReportsFragmentBottomNav extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reports_bottom_nav, container, false);
        GraphView graphView = root.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 0),
                new DataPoint(6, 300),
                new DataPoint(11, 1300),
                new DataPoint(19, 1500),
                new DataPoint(20, 3060),
                new DataPoint(25, 3605)
        });
        graphView.addSeries(series);
        graphView.setTitle("August summary");
        return root;
    }
}