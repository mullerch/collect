package com.hesso.mse.collect;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "collect")
public class mCollect {

    @DatabaseField(generatedId = true)
    private int id;

    private Location location;

    @DatabaseField
    private Date date;

    @DatabaseField
    private String comment;

    mCollect() {

    }

    public mCollect(Location location, Date date, String comment) {
        this.location = location;
        this.date = date;
        this.comment = comment;
    }

    public mCollect(Location location, String comment) {
        //this.date = new Date(millis);
        this.location = location;
        this.comment = comment;
    }

}
