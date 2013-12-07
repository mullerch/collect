package com.hesso.mse.collect.device;

import android.content.Context;

import com.hesso.mse.collect.DatabaseHelper;
import com.hesso.mse.collect.DatabaseManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class DeviceRepository {

        private DatabaseHelper db;
        Dao<Device, Integer> deviceDao;

        public DeviceRepository(Context ctx)
        {
            try {
                DatabaseManager dbManager = new DatabaseManager();
                db = dbManager.getHelper(ctx);
                deviceDao = db.getDeviceDao();
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }

        }

        public int create(Device device)
        {
            try {
                return deviceDao.create(device);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }
        public int update(Device device)
        {
            try {
                return deviceDao.update(device);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }
        public int delete(Device device)
        {
            try {
                return deviceDao.delete(device);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }

        public List getAll()
        {
            try {
                return deviceDao.queryForAll();
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return null;
        }
}
