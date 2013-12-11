package com.hesso.mse.collect;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "data")
public class mData {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int value;

    mData() {

    }

    public mData(int value) {
        this.value = value;
    }
}
