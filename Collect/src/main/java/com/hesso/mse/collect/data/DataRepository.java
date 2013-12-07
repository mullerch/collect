package com.hesso.mse.collect.data;

import android.content.Context;

import com.hesso.mse.collect.DatabaseHelper;
import com.hesso.mse.collect.DatabaseManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;



public class DataRepository {

        private DatabaseHelper db;
        Dao<Data, Integer> dataDao;

        public DataRepository(Context ctx)
        {
            try {
                DatabaseManager dbManager = new DatabaseManager();
                db = dbManager.getHelper(ctx);
                dataDao = db.getDataDao();
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }

        }

        public int create(Data data)
        {
            try {
                return dataDao.create(data);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }
        public int update(Data data)
        {
            try {
                return dataDao.update(data);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }
        public int delete(Data data)
        {
            try {
                return dataDao.delete(data);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }

        public List getAll()
        {
            try {
                return dataDao.queryForAll();
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return null;
        }
}
