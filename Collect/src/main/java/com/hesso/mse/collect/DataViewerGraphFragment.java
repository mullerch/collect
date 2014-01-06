package com.hesso.mse.collect;

import android.app.Fragment;
import android.os.Bundle;
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

        GraphView.GraphViewData[] data = new GraphView.GraphViewData[dataList.size()];

        for (int i=0; i<dataList.size(); ++i) {
            data[i] = new GraphView.GraphViewData(dataList.get(i).getTime(), dataList.get(i).getValue());
        }

        GraphView graphView = new LineGraphView(getActivity() , "DataViewer");

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