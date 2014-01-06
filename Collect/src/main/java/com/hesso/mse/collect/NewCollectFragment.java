package com.hesso.mse.collect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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


public class NewCollectFragment extends Fragment {

    protected TextView lblLocation;
    protected ProgressDialog mProgressDialog;
    protected mDevice device;
    protected Location currentLocation;

    /**
     * @return A new instance of fragment NewCollectFragment.
     */
    public NewCollectFragment() {
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

        final String collectData = this.getArguments().getString("COLLECT_DATA");


        ArrayList<Integer> collectDataValues = new ArrayList<Integer>();

        StringTokenizer tokenizer = new StringTokenizer(collectData, ";");

        /* Get start time and step */
        String id;
        int time, step;
        try {
            id = tokenizer.nextToken();
            time = Integer.parseInt(tokenizer.nextToken());
            step = Integer.parseInt(tokenizer.nextToken());
        } catch (Exception e) {

            return rootView;
        }

        /* Extract values from NDEF */
        while (tokenizer.hasMoreElements()) {
            try {
                collectDataValues.add(Integer.parseInt(tokenizer.nextToken()));
            } catch (Exception e) {
                Log.i("TAG", "Value was no int");
            }
        }

        final ArrayList<Integer> localCollectDataValues = collectDataValues;

        /* Compute end time */
        final int startTime = time;
        final int endTime = startTime+collectDataValues.size()*step;
        final int mesureStep = step;
        final String macId = id;

        /* Create start and end time in string */
        String startTimeString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(startTime*1000L));
        String endTimeString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(endTime*1000L));

        TextView lblDeviceId = (TextView) rootView.findViewById(R.id.lblDeviceId);
        TextView lblDeviceDesc = (TextView) rootView.findViewById(R.id.lblDeviceDesc);

        TextView lblCaptureStart = (TextView) rootView.findViewById(R.id.lblCaptureStart);
        TextView lblCaptureEnd = (TextView) rootView.findViewById(R.id.lblCaptureEnd);
        TextView lblStep = (TextView) rootView.findViewById(R.id.lblStep);
        TextView lblNbEntries = (TextView) rootView.findViewById(R.id.lblNbEntries);

        lblLocation = (TextView) rootView.findViewById(R.id.lblLocation);

        Button btnSave = (Button) rootView.findViewById(R.id.btnCollectSave);
        Button btnCancel = (Button) rootView.findViewById(R.id.btnCollectCancel);

        final EditText txtDeviceDescription = (EditText) rootView.findViewById(R.id.txtDescription);
        final EditText txtCollectDescription = (EditText) rootView.findViewById(R.id.txtCollectDescription);


        lblDeviceId.append(id);

        List<mDevice> devices = getHelper().getRuntimeDeviceDao().queryForEq("MAC_ID", macId);


        if (devices.size() != 1) {
            lblDeviceDesc.setText("Description for new device : ");
        }
        else {
            device = devices.get(0);
            lblDeviceDesc.append(device.getDescription());
            ((ViewManager) txtDeviceDescription.getParent()).removeView(txtDeviceDescription);
        }

        lblCaptureStart.append(startTimeString);
        lblCaptureEnd.append(endTimeString);
        lblStep.append(mesureStep + "s");
        lblNbEntries.append("" + collectDataValues.size());


        LocationManager mLocManager;

        // Get the location manager
        try {
            mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            mLocManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new MyLocationListener(), null);
        } catch (Exception e) {
            Log.i("NewCollectDialogFragment", "cannot get the location manager : " + e.getStackTrace());
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Waiting progress dialog */
                mProgressDialog = ProgressDialog.show(getActivity(), "Please wait", "Saving collect to database", true);

                /* Save collect and data to database */
                new Thread((new Runnable() {
                    @Override
                    public void run() {

                        if (device == null)
                            device = new mDevice(macId, txtDeviceDescription.getText().toString());

                        mCollect collect = new mCollect(currentLocation.getLongitude() + "/" + currentLocation.getLatitude(), txtCollectDescription.getText().toString(), device);

                        collect.setStartTime(startTime);
                        collect.setEndTime(endTime);
                        collect.setStep(mesureStep);

                        getHelper().getRuntimeCollectDao().create(collect);

                        int dataTime = startTime;

                        for (Iterator<Integer> i = localCollectDataValues.iterator(); i.hasNext(); ) {
                            getHelper().getRuntimeDataDao().create(new mData(i.next(), dataTime, collect));
                            dataTime += mesureStep;
                        }

                        mProgressDialog.dismiss();

                    }
                })).start();

                getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            }
        });


        return rootView;
    }



    /**
     * LocationListener inner class
     * @author romain
     *
     */
    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            Log.i("onLocationChanged", "got a location update: lat=" + lat + " / lon=" + lon);
            lblLocation.append("lat=" + lat + " / lon=" + lon);
            currentLocation = location;
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

}
