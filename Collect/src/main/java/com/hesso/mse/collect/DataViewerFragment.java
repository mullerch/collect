package com.hesso.mse.collect;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataViewerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_data_viewer, container, false);
        final FragmentManager fragmentManager = getFragmentManager();

        ListView collectionsListView = (ListView) rootView.findViewById(R.id.deviceList);

        String[] values = new String[] { "Device 1", "Device 2", "Device 3" };

        final ArrayList<String> collectionList = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            collectionList.add(values[i]);
        }


        final DevicesArrayAdapter adapter = new DevicesArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, collectionList);
        collectionsListView.setAdapter(adapter);

        collectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                /* Opens the graph visualisation fragment */
                fragmentManager.beginTransaction().replace(R.id.content_fragment_container, new DataViewerGraphFragment()).addToBackStack(null).commit();
            }

        });

        return rootView;
    }


    /* Devices array adapter */
    private class DevicesArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public DevicesArrayAdapter(Context context, int textViewResourceId,
                                   List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}