package com.hesso.mse.collect;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class ShowCollectFragment extends Fragment {

    /**
     * @return A new instance of fragment NewCollectFragment.
     */
    public ShowCollectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_collect, container, false);

        mCollect collect = getHelper().getRuntimeCollectDao().queryForId(this.getArguments().getInt("COLLECT_ID"));

        /* Create start and end time in string */
        String startTimeString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(collect.getStartTime()*1000L));
        String endTimeString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(collect.getEndTime()*1000L));

        TextView lblDeviceId = (TextView) rootView.findViewById(R.id.lblDeviceId);
        TextView lblDeviceDesc = (TextView) rootView.findViewById(R.id.lblDeviceDesc);

        TextView lblCaptureStart = (TextView) rootView.findViewById(R.id.lblCaptureStart);
        TextView lblCaptureEnd = (TextView) rootView.findViewById(R.id.lblCaptureEnd);
        TextView lblStep = (TextView) rootView.findViewById(R.id.lblStep);
        TextView lblNbEntries = (TextView) rootView.findViewById(R.id.lblNbEntries);
        TextView lblCollectDescription = (TextView) rootView.findViewById(R.id.lblCollectDescription);

        TextView lblLocation = (TextView) rootView.findViewById(R.id.lblLocation);

        Button btnSave = (Button) rootView.findViewById(R.id.btnCollectSave);
        Button btnCancel = (Button) rootView.findViewById(R.id.btnCollectCancel);

        EditText txtDeviceDescription = (EditText) rootView.findViewById(R.id.txtDescription);
        EditText txtCollectDescription = (EditText) rootView.findViewById(R.id.txtCollectDescription);

        lblDeviceId.append(collect.getDevice().getMacId());
        lblDeviceDesc.append(collect.getDevice().getDescription());

        ((ViewManager) txtDeviceDescription.getParent()).removeView(txtDeviceDescription);
        ((ViewManager) txtCollectDescription.getParent()).removeView(txtCollectDescription);
        ((ViewManager) btnSave.getParent()).removeView(btnSave);
        ((ViewManager) btnCancel.getParent()).removeView(btnCancel);

        lblCaptureStart.append(startTimeString);
        lblCaptureEnd.append(endTimeString);
        lblStep.append(collect.getStep() + "s");
        lblNbEntries.append("" + collect.getDataSet().size());
        lblCollectDescription.append(collect.getComment());
        lblLocation.append(collect.getLocation());

        return rootView;
    }

}
