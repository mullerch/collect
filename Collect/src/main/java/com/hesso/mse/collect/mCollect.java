package com.hesso.mse.collect;

import android.location.Location;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@DatabaseTable(tableName = "collect")
public class mCollect {

    @DatabaseField(generatedId = true)
    private int id;

    private Location location;

    @DatabaseField
    private long timestamp;

    @DatabaseField
    private String comment;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<mData> dataSet;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "idx_device")
    private mDevice device;

    mCollect() {

    }

    /**
     * Constructor
     * @param location Collect location
     * @param timestamp Collect date
     * @param comment Comment to the collect
     */
    public mCollect(Location location, int timestamp, String comment, Collection<mData> dataSet, mDevice device) {
        this.location = location;
        this.timestamp = timestamp;
        this.comment = comment;
        this.dataSet = (ForeignCollection<mData>) dataSet;
        this.device = device;
    }

    /**
     * Constructor
     * @param location Collect location
     * @param comment Collect
     */
    public mCollect(Location location, String comment, Collection<mData> dataSet, mDevice device) {

        Calendar c = Calendar.getInstance();
        int timestamp = c.get(Calendar.SECOND);

        this.location = location;
        this.timestamp = timestamp;
        this.comment = comment;
        this.dataSet = (ForeignCollection<mData>) dataSet;
        this.device = device;
    }

    public mCollect(Location location, String comment, mDevice device) {
        Calendar c = Calendar.getInstance();
        long timestamp = System.currentTimeMillis();

        this.location = location;
        this.timestamp = timestamp;
        this.comment = comment;
        this.device = device;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Collection<mData> getDataSet() {
        return dataSet;
    }

    public mDevice getDevice() {
        return device;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(timestamp));
    }

    public String getLocationString() {
        return location.toString();
    }
}
