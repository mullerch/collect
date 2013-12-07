package com.hesso.mse.collect.collect;

import android.content.Context;

import com.hesso.mse.collect.DatabaseHelper;
import com.hesso.mse.collect.DatabaseManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class CollectRepository {

        private DatabaseHelper db;
        Dao<Collect, Integer> collectDao;

        public CollectRepository(Context ctx)
        {
            try {
                DatabaseManager dbManager = new DatabaseManager();
                db = dbManager.getHelper(ctx);
                collectDao = db.getCollectDao();
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }

        }

        public int create(Collect collect)
        {
            try {
                return collectDao.create(collect);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }
        public int update(Collect collect)
        {
            try {
                return collectDao.update(collect);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }
        public int delete(Collect collect)
        {
            try {
                return collectDao.delete(collect);
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return 0;
        }

        public List getAll()
        {
            try {
                return collectDao.queryForAll();
            } catch (SQLException e) {
                // TODO: Exception Handling
                e.printStackTrace();
            }
            return null;
        }
}
