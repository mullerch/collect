package com.hesso.mse.collect;

import java.sql.SQLException;

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
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the objects table
    private Dao<mData, Integer> dataDao = null;
    private RuntimeExceptionDao<mData, Integer> dataRuntimeDao = null;


    private Dao<mCollect, Integer> collectDao;
    private RuntimeExceptionDao<mCollect, Integer> collectRuntimeDao = null;

    private Dao<mDevice, Integer> deviceDao;
    private RuntimeExceptionDao<mDevice, Integer> deviceRuntimeDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

        getRuntimeDeviceDao().create(new mDevice("Humidity captor - Yverdon"));
        getRuntimeDeviceDao().create(new mDevice("Humidity captor - Lausanne"));
        getRuntimeDeviceDao().create(new mDevice("Lumen captor - Lausanne"));

        getRuntimeDataDao().create(new mData(76));
        getRuntimeDataDao().create(new mData(23));
        getRuntimeDataDao().create(new mData(45));
        getRuntimeDataDao().create(new mData(12));
        getRuntimeDataDao().create(new mData(456));
        getRuntimeDataDao().create(new mData(23));

        getRuntimeCollectDao().create(new mCollect(null, "Collecte 1"));
        getRuntimeCollectDao().create(new mCollect(null, "Collecte 2"));
        getRuntimeCollectDao().create(new mCollect(null, "Collecte 3"));
        getRuntimeCollectDao().create(new mCollect(null, "Collecte 4"));

    }

}