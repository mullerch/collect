package com.hesso.mse.collect;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;


public class DataViewerFragment extends Fragment {


    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_data_viewer, container, false);
        final FragmentManager fragmentManager = getFragmentManager();

        /* Get data list from DB */
        ListView deviceListView = (ListView) rootView.findViewById(R.id.deviceList);

        ArrayList<mDevice> deviceList = (ArrayList<mDevice>) getHelper().getRuntimeDeviceDao().queryForAll();

        /*  */
        DeviceArrayAdapter adapter = new DeviceArrayAdapter(this.getActivity(), R.id.deviceList, deviceList);
        deviceListView.setAdapter(adapter);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Fragment graphVisualisationFragment = new DataViewerGraphFragment();

                mDevice device = (mDevice) parent.getItemAtPosition(position);

                /* Add params to fragment */
                Bundle bundle = new Bundle();
                bundle.putInt("DEVICE_ID", device.getId());
                graphVisualisationFragment.setArguments(bundle);

                /* Opens the graph visualisation fragment */
                fragmentManager.beginTransaction().replace(R.id.content_fragment_container, graphVisualisationFragment).addToBackStack(null).commit();
            }

        });

        return rootView;
    }


    /* Devices array adapter */
    private class DeviceArrayAdapter extends ArrayAdapter<mDevice> {


        private ArrayList<mDevice> entries;
        private Activity activity;

        public DeviceArrayAdapter(Activity a, int textViewResourceId, ArrayList<mDevice> entries) {
            super(a, textViewResourceId, entries);
            this.entries = entries;
            this.activity = a;
        }

        public class ViewHolder{
            public TextView deviceName;
            public TextView deviceLocation;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if (v == null) {
                LayoutInflater vi =
                        (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.device_item, null);
                holder = new ViewHolder();
                holder.deviceName = (TextView) v.findViewById(R.id.device_name);
                holder.deviceLocation = (TextView) v.findViewById(R.id.device_location);
                v.setTag(holder);
            }
            else
                holder=(ViewHolder)v.getTag();

            final mDevice device = entries.get(position);
            if (device != null) {
                holder.deviceName.setText(device.getDescription());
                holder.deviceLocation.setText(device.getLastKnownLocation());
            }
            return v;
        }

    }
}