package com.hesso.mse.collect.collect;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "collect")
public class Collect {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Location location;

    @DatabaseField
    private Date date;

    @DatabaseField
    private String comment;

    Collect() {

    }

    public Collect(Location location, Date date, String comment) {
        this.location = location;
        this.date = date;
        this.comment = comment;
    }

    public Collect(Location location, String comment) {
        //this.date = new Date(millis);
        this.location = location;
        this.comment = comment;
    }

}
