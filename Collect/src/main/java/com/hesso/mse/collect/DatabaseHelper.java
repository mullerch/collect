package com.hesso.mse.collect;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hesso.mse.collect.collect.Collect;
import com.hesso.mse.collect.device.Device;
import com.hesso.mse.collect.data.Data;

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

    // the DAO object we use to access the SimpleData table
    private Dao<Data, Integer> dataDao = null;
    private Dao<Collect, Integer> collectDao = null;
    private Dao<Device, Integer> deviceDao = null;

    private RuntimeExceptionDao<Data, Integer> simpleRuntimeDataDao = null;
    private RuntimeExceptionDao<Collect, Integer> simpleRuntimeCollectDao = null;
    private RuntimeExceptionDao<Device, Integer> simpleRuntimeDeviceDao = null;

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
            TableUtils.createTable(connectionSource, Data.class);
            TableUtils.createTable(connectionSource, Collect.class);
            TableUtils.createTable(connectionSource, Device.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<Data, Integer> dataDao = getRuntimeDataDao();
        RuntimeExceptionDao<Collect, Integer> collectDao = getRuntimeCollectDao();
        RuntimeExceptionDao<Device, Integer> deviceDao = getRuntimeDeviceDao();

        // create some entries in the onCreate
        Data data = new Data(23);
        dataDao.create(data);
        data = new Data(125);
        dataDao.create(data);

        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate");
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Data.class, true);
            TableUtils.dropTable(connectionSource, Collect.class, true);
            TableUtils.dropTable(connectionSource, Device.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Data, Integer> getDataDao() throws SQLException {
        if (dataDao == null) {
            dataDao = getDao(Data.class);
        }
        return dataDao;
    }

    public Dao<Collect, Integer> getCollectDao() throws SQLException {
        if (collectDao == null) {
            collectDao = getDao(Collect.class);
        }
        return collectDao;
    }


    public Dao<Device, Integer> getDeviceDao() throws SQLException {
        if (deviceDao == null) {
            deviceDao = getDao(Device.class);
        }
        return deviceDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Data, Integer> getRuntimeDataDao() {
        if (simpleRuntimeDataDao == null) {
            simpleRuntimeDataDao = getRuntimeExceptionDao(Data.class);
        }
        return simpleRuntimeDataDao;
    }

    public RuntimeExceptionDao<Collect, Integer> getRuntimeCollectDao() {
        if (simpleRuntimeCollectDao == null) {
            simpleRuntimeCollectDao = getRuntimeExceptionDao(Collect.class);
        }
        return simpleRuntimeCollectDao;
    }

    public RuntimeExceptionDao<Device, Integer> getRuntimeDeviceDao() {
        if (simpleRuntimeDeviceDao == null) {
            simpleRuntimeDeviceDao = getRuntimeExceptionDao(Device.class);
        }
        return simpleRuntimeDeviceDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        simpleRuntimeCollectDao = null;
        simpleRuntimeDataDao = null;
        simpleRuntimeDeviceDao = null;
    }
}