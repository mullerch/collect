package com.hesso.mse.collect;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.TimeStampType;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "data")
public class mData {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int value;

    @DatabaseField
    private int time;

    @DatabaseField(foreign = true, columnName = "idx_collect")
    private mCollect collect;

    mData() {

    }

    public mData(int value, int time, mCollect collect) {
        this.value = value;
        this.time = time;
        this.collect = collect;
    }


    public int getValue() {
        return value;
    }
}
