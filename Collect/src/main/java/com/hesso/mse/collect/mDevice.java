package com.hesso.mse.collect;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "device")
public class mDevice {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String description;

    private Location lastKnownLocation;

    mDevice() {

    }

    public mDevice(String description) {
        this.description = description;
    }

    public Location getLastKnownLocation() {

        return lastKnownLocation;
    }
    public String getDescription() { return description; }
}
