package com.hesso.mse.collect;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.jjoe64.graphview.*;

import java.util.ArrayList;


public class DataViewerGraphFragment extends Fragment {

    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.fragment_data_viewer_graph, null);

        /* Retrieve args */

        mDevice device = getHelper().getRuntimeDeviceDao().queryForId(this.getArguments().getInt("DEVICE_ID"));

        ArrayList<mData> dataList = device.getData();

        /*
        ArrayList<mCollect> collectList = (ArrayList<mCollect>) getHelper().getRuntimeCollectDao().queryForEq("idx_device", deviceId);

        ArrayList<mData> dataList = new ArrayList<mData>();

        for(int i=0; i<collectList.size(); i++) {
            Log.i("", "Collect '" + collectList.get(i).getComment() + "' is matching");
            dataList.addAll(getHelper().getRuntimeDataDao().queryForEq("idx_collect", collectList.get(i).getId()));
        }
        */

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

/*
        // draw sin curve
        int num = 150;
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[num];
        double v=0;
        for (int i=0; i<num; i++) {
            v += 0.2;
            data[i] = new GraphView.GraphViewData(i, Math.sin(v));
        }

*/

        GraphView.GraphViewData[] data = new GraphView.GraphViewData[dataList.size()];

        Log.i("GraphData", "Adding graph data : " + dataList.size());

        for (int i=0; i<dataList.size(); ++i) {
            data[i] = new GraphView.GraphViewData(i, dataList.get(i).getValue());
            Log.i("GraphData", "new data added : " + dataList.get(i).getValue());
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