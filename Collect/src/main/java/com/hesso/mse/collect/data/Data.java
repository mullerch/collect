package com.hesso.mse.collect.data;

import android.location.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "data")
public class Data {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int value;

    Data() {

    }

    public Data(int value) {
        this.value = value;
    }
}
