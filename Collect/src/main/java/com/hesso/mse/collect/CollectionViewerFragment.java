package com.hesso.mse.collect;

import android.app.Fragment;
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

/**
 * Created by christian on 11/19/13.
 */
public class CollectionViewerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_collection_viewer, container, false);

        ListView collectionsListView = (ListView) view.findViewById(R.id.collectionsList);

        String[] values = new String[] { "Data1", "Data2", "Data3", "Data4", "Data5", "Data6",
                "Data7", "Data8", "Data9", "Data10", "Data11", "Data12", "Data13", "Data14",
                "Data15", "Data16" };

        final ArrayList<String> collectionList = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            collectionList.add(values[i]);
        }


        final StableArrayAdapter adapter = new StableArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, collectionList);
        collectionsListView.setAdapter(adapter);

        collectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                collectionList.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });

        return view;
    }





    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
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