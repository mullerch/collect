package com.hesso.mse.collect;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.*;

/**
 * Created by christian on 11/26/13.
 */
public class DataViewerGraphFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.fragment_data_viewer_graph, null);
/*
        // init example series data
        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphView.GraphViewData[] {
                new GraphView.GraphViewData(1, 2.0d)
                , new GraphView.GraphViewData(2, 1.5d)
                , new GraphView.GraphViewData(3, 2.5d)
                , new GraphView.GraphViewData(4, 1.0d)
        });

        GraphView graphView = new LineGraphView(
                getActivity() // context
                , "GraphViewDemo" // heading
        );
        graphView.addSeries(exampleSeries); // data

        viewGroup.addView(graphView);
*/


        // draw sin curve
        int num = 150;
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[num];
        double v=0;
        for (int i=0; i<num; i++) {
            v += 0.2;
            data[i] = new GraphView.GraphViewData(i, Math.sin(v));
        }
        GraphView graphView = new LineGraphView(getActivity() , "GraphViewDemo");

        // add data
        graphView.addSeries(new GraphViewSeries(data));

        // set view port, start=2, size=40
        graphView.setViewPort(2, 40);
        graphView.setScrollable(true);

        // optional - activate scaling / zooming
        graphView.setScalable(true);

        viewGroup.addView(graphView);

        return viewGroup;
    }
}