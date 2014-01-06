package com.hesso.mse.collect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class NewCollectDialogFragment extends DialogFragment {

    private DatabaseHelper databaseHelper = null;
    protected ProgressDialog mProgressDialog;

    protected DatabaseHelper getHelper() {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String collectData = this.getArguments().getString("COLLECT_DATA");

        ArrayList<Integer> collectDataValues = new ArrayList<Integer>();

        StringTokenizer tokenizer = new StringTokenizer(collectData, ";");

        builder.setTitle("New collect");


        /* Get start time and step */
        String id;
        int time, step;
        try {
            id = tokenizer.nextToken();
            time = Integer.parseInt(tokenizer.nextToken());
            step = Integer.parseInt(tokenizer.nextToken());
        } catch (Exception e) {

            return builder.setMessage("Error, scanned data is missing device id, date and/or step for data mesures")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create();
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

        /* Build dialog */
        builder.setMessage("Received data from " + macId +"\nNumber of values : " + collectDataValues.size() + "\nMesures start : " + startTimeString + "\nStep : " + step + " sec\nMesure end : " + endTimeString)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /* Waiting progress dialog */
                        mProgressDialog = ProgressDialog.show(getActivity(), "Please wait", "Saving collect to database", true);

                        /* Save collect and data to database */
                        new Thread((new Runnable() {
                            @Override
                            public void run() {

                                List<mDevice> devices = getHelper().getRuntimeDeviceDao().queryForEq("MAC_ID", macId);

                                LocationManager mLocManager;

                                // Get the location manager
                                //try {
                                mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                                mLocManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new MyLocationListener(), null);
                                //} catch (Exception e) {
                                //    Log.i("NewCollectDialogFragment", "cannot get the location manager : " + e.getStackTrace());
                                //}

                                mDevice device;
                                if (devices.size() != 1)
                                    device = new mDevice(macId, macId);
                                else
                                    device = devices.get(0);

                                mCollect collect = new mCollect(null, "Test collect", device);

                                getHelper().getRuntimeCollectDao().create(collect);

                                int dataTime = startTime;

                                for (Iterator<Integer> i = localCollectDataValues.iterator(); i.hasNext(); ) {
                                    getHelper().getRuntimeDataDao().create(new mData(i.next(), dataTime, collect));
                                    dataTime += mesureStep;
                                }

                                mProgressDialog.dismiss();
                            }
                        })).start();

                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
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
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
