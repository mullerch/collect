package com.hesso.mse.collect;

import android.location.Location;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@DatabaseTable(tableName = "device")
public class mDevice {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String description;

    @DatabaseField(index = true, columnName = "MAC_ID")
    private String macId;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<mCollect> collectList;

    private Location lastKnownLocation;

    mDevice() {

    }

    public mDevice(String macId, String description) {
        this.macId = macId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getMacId() {
        return macId;
    }

    /**
     * @return Last device known location due to referenced collects
     */
    public String getLastKnownLocation() {

        long newest = 0;
        String last = "No location found";

        for(mCollect collect: collectList) {
            if(collect.getTimestamp() > newest) {
                newest = collect.getTimestamp();
                last = collect.getLocation();
            }
        }

        return last;
    }

    /**
     * @return Device readable description
     */
    public String getDescription() { return description; }

    public ArrayList<mData> getData() {
        ArrayList<mData> deviceData = new ArrayList<mData>();

        for(Iterator<mCollect> collect = collectList.iterator(); collect.hasNext(); ) {
            deviceData.addAll(collect.next().getDataSet());
        }

        return deviceData;
    }
}
