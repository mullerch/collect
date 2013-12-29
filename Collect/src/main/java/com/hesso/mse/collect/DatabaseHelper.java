package com.hesso.mse.collect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "collect.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2;

    // the DAO object we use to access the objects table
    private Dao<mData, Integer> dataDao = null;
    private RuntimeExceptionDao<mData, Integer> dataRuntimeDao = null;


    private Dao<mCollect, Integer> collectDao;
    private RuntimeExceptionDao<mCollect, Integer> collectRuntimeDao = null;

    private Dao<mDevice, Integer> deviceDao;
    private RuntimeExceptionDao<mDevice, Integer> deviceRuntimeDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, mData.class);
            TableUtils.createTable(connectionSource, mCollect.class);
            TableUtils.createTable(connectionSource, mDevice.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
        }


        createSampleData();
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, mData.class, true);
            TableUtils.dropTable(connectionSource, mCollect.class, true);
            TableUtils.dropTable(connectionSource, mDevice.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<mData, Integer> getDataDao() throws SQLException {
        if (dataDao == null) {
            dataDao = getDao(mData.class);
        }
        return dataDao;
    }

    public Dao<mCollect, Integer> getCollectDao() throws SQLException {
        if (collectDao == null) {
            collectDao = getDao(mCollect.class);
        }
        return collectDao;
    }


    public Dao<mDevice, Integer> getDeviceDao() throws SQLException {
        if (deviceDao == null) {
            deviceDao = getDao(mDevice.class);
        }
        return deviceDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<mData, Integer> getRuntimeDataDao() {
        if (dataRuntimeDao == null) {
            dataRuntimeDao = getRuntimeExceptionDao(mData.class);
        }
        return dataRuntimeDao;
    }


    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<mCollect, Integer> getRuntimeCollectDao() {
        if (collectRuntimeDao == null) {
            collectRuntimeDao = getRuntimeExceptionDao(mCollect.class);
        }
        return collectRuntimeDao;
    }


    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<mDevice, Integer> getRuntimeDeviceDao() {
        if (deviceRuntimeDao == null) {
            deviceRuntimeDao = getRuntimeExceptionDao(mDevice.class);
        }
        return deviceRuntimeDao;
    }

    private void createSampleData() {


/*
        ArrayList<mCollect> collects = new ArrayList<mCollect>();

        mDevice device1 = new mDevice("Humidity captor - Yverdon");

        collects.add(new mCollect(null, "Collecte 1", device1));
        collects.add(new mCollect(null, "Collecte 2", device1));
        collects.add(new mCollect(null, "Collecte 3", new mDevice("Humidity captor - Lausanne")));
        collects.add(new mCollect(null, "Collecte 4", new mDevice("Lumen captor - Lausanne")));


        for(Iterator<mCollect> i = collects.iterator(); i.hasNext(); ) {
            getRuntimeCollectDao().create(i.next());
        }


        getRuntimeDataDao().create(new mData(1, collects.get(0)));
        getRuntimeDataDao().create(new mData(2, collects.get(0)));
        getRuntimeDataDao().create(new mData(3, collects.get(0)));
        getRuntimeDataDao().create(new mData(4, collects.get(0)));
        getRuntimeDataDao().create(new mData(5, collects.get(0)));
        getRuntimeDataDao().create(new mData(6, collects.get(0)));
        getRuntimeDataDao().create(new mData(7, collects.get(0)));
        getRuntimeDataDao().create(new mData(8, collects.get(0)));
        getRuntimeDataDao().create(new mData(9, collects.get(0)));
        getRuntimeDataDao().create(new mData(25, collects.get(0)));
        getRuntimeDataDao().create(new mData(26, collects.get(0)));
        getRuntimeDataDao().create(new mData(27, collects.get(0)));
        getRuntimeDataDao().create(new mData(28, collects.get(0)));
        getRuntimeDataDao().create(new mData(29, collects.get(0)));
        getRuntimeDataDao().create(new mData(30, collects.get(0)));
        getRuntimeDataDao().create(new mData(31, collects.get(0)));
        getRuntimeDataDao().create(new mData(32, collects.get(0)));
        getRuntimeDataDao().create(new mData(33, collects.get(0)));
        getRuntimeDataDao().create(new mData(34, collects.get(0)));
        getRuntimeDataDao().create(new mData(35, collects.get(0)));
        getRuntimeDataDao().create(new mData(36, collects.get(0)));
        getRuntimeDataDao().create(new mData(37, collects.get(0)));
        getRuntimeDataDao().create(new mData(38, collects.get(0)));
        getRuntimeDataDao().create(new mData(39, collects.get(0)));
        getRuntimeDataDao().create(new mData(40, collects.get(0)));
        getRuntimeDataDao().create(new mData(41, collects.get(0)));
        getRuntimeDataDao().create(new mData(42, collects.get(0)));


        getRuntimeDataDao().create(new mData(10, collects.get(1)));
        getRuntimeDataDao().create(new mData(11, collects.get(1)));
        getRuntimeDataDao().create(new mData(12, collects.get(1)));
        getRuntimeDataDao().create(new mData(13, collects.get(1)));
        getRuntimeDataDao().create(new mData(14, collects.get(1)));
        getRuntimeDataDao().create(new mData(15, collects.get(1)));
        getRuntimeDataDao().create(new mData(16, collects.get(1)));
        getRuntimeDataDao().create(new mData(17, collects.get(1)));
        getRuntimeDataDao().create(new mData(18, collects.get(1)));
        getRuntimeDataDao().create(new mData(19, collects.get(1)));
        getRuntimeDataDao().create(new mData(20, collects.get(1)));
        getRuntimeDataDao().create(new mData(21, collects.get(1)));
        getRuntimeDataDao().create(new mData(22, collects.get(1)));
        getRuntimeDataDao().create(new mData(23, collects.get(1)));
        getRuntimeDataDao().create(new mData(24, collects.get(1)));


        getRuntimeDataDao().create(new mData(23, collects.get(2)));
        getRuntimeDataDao().create(new mData(24, collects.get(2)));


        getRuntimeDataDao().create(new mData(23, collects.get(3)));
        getRuntimeDataDao().create(new mData(24, collects.get(3)));

*/
    }

}