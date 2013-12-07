package com.hesso.mse.collect.device;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "device")
public class Device {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String description;

    private Location lastKnownLocation;

    Device() {

    }

    public Device(String description) {
        this.description = description;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }
}
