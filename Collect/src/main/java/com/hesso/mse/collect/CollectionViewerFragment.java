package com.hesso.mse.collect;

import android.app.Activity;
import android.app.Fragment;
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


public class CollectionViewerFragment extends Fragment {

    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_collection_viewer, container, false);

        /* Get data list from DB */
        ListView collectListView = (ListView) rootView.findViewById(R.id.collectionsList);

        ArrayList<mCollect> collectList = (ArrayList<mCollect>) getHelper().getRuntimeCollectDao().queryForAll();

        /*  */
        CollectArrayAdapter adapter = new CollectArrayAdapter(this.getActivity(), R.id.collectionsList, collectList);
        collectListView.setAdapter(adapter);


        collectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //TODO: implement map fragment opening

            }

        });

        return rootView;
    }



    /* Collect array adapter */
    private class CollectArrayAdapter extends ArrayAdapter<mCollect> {


        private ArrayList<mCollect> entries;
        private Activity activity;

        public CollectArrayAdapter(Activity a, int textViewResourceId, ArrayList<mCollect> entries) {
            super(a, textViewResourceId, entries);
            this.entries = entries;
            this.activity = a;
        }

        public class ViewHolder{
            public TextView collectComment;
            public TextView collectInfo;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if (v == null) {
                LayoutInflater vi =
                        (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.collect_item, null);
                holder = new ViewHolder();
                holder.collectComment = (TextView) v.findViewById(R.id.collect_comment);
                holder.collectInfo = (TextView) v.findViewById(R.id.collect_info);
                v.setTag(holder);
            }
            else
                holder=(ViewHolder)v.getTag();

            final mCollect collect = entries.get(position);
            if (collect != null) {
                holder.collectComment.setText(collect.getComment());
                holder.collectInfo.setText(collect.getDevice().getDescription() + " - " + collect.getDate());
            }
            return v;
        }

    }
}