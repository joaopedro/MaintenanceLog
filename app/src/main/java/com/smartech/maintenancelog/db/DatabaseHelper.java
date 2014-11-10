package com.smartech.maintenancelog.db;

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
    private static final String DATABASE_NAME = "maintenancelog.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the Login table
    private Dao<Login, Long> loginDao = null;
    private RuntimeExceptionDao<Login, Long> loginRuntimeDao = null;

    // the DAO object we use to access the Ordem table
    private Dao<Ordem, Long> ordemDao = null;
    private RuntimeExceptionDao<Ordem, Long> ordemRuntimeDao = null;

    // the DAO object we use to access the Equipamento table
    private Dao<Equipamento, Long> equipamentoDao = null;
    private RuntimeExceptionDao<Equipamento, Long> equipamentoRuntimeDao = null;
    private RuntimeExceptionDao<Activity, Long> activityRuntimeDao = null;
    private Dao<Activity, Long> activityDao = null;
    private RuntimeExceptionDao<Part, Long> partRuntimeDao = null;
    private Dao<Part, Long> partDao = null;

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
            TableUtils.createTable(connectionSource, Login.class);
            TableUtils.createTable(connectionSource, Part.class);
            TableUtils.createTable(connectionSource, Activity.class);
            TableUtils.createTable(connectionSource, HistoryEntry.class);
            TableUtils.createTable(connectionSource, Equipamento.class);
            TableUtils.createTable(connectionSource, Ordem.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Login.class, true);
            TableUtils.dropTable(connectionSource, Ordem.class, true);
            TableUtils.dropTable(connectionSource, Equipamento.class, true);
            TableUtils.dropTable(connectionSource, HistoryEntry.class, true);
            TableUtils.dropTable(connectionSource, Part.class, true);
            TableUtils.dropTable(connectionSource, Activity.class, true);
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
    public Dao<Login, Long> getLoginDao() throws SQLException {
        if (loginDao == null) {
            loginDao = getDao(Login.class);
        }
        return loginDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Login, Long> getLoginRuntimeDao() {
        if (loginRuntimeDao == null) {
            loginRuntimeDao = getRuntimeExceptionDao(Login.class);
        }
        return loginRuntimeDao;
    }

    public Dao<Ordem, Long> getOrdemDao() throws SQLException {
        if (ordemDao == null) {
            ordemDao = getDao(Ordem.class);
        }
        return ordemDao;
    }
    public RuntimeExceptionDao<Ordem, Long> getOrdemRuntimeDao() {
        if (ordemRuntimeDao == null) {
            ordemRuntimeDao = getRuntimeExceptionDao(Ordem.class);
        }
        return ordemRuntimeDao;
    }

    public Dao<Equipamento, Long> getEquipamentoDao() throws SQLException {
        if (equipamentoDao == null) {
            equipamentoDao = getDao(Equipamento.class);
        }
        return equipamentoDao;
    }
    public RuntimeExceptionDao<Equipamento, Long> getEquipamentoRuntimeDao() {
        if (equipamentoRuntimeDao == null) {
            equipamentoRuntimeDao = getRuntimeExceptionDao(Equipamento.class);
        }
        return equipamentoRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        loginDao = null;
        loginRuntimeDao = null;
        ordemDao = null;
        ordemRuntimeDao = null;

    }

    public RuntimeExceptionDao<Activity, Long> getActivityRuntimeDao() {
        if (activityRuntimeDao == null) {
            activityRuntimeDao = getRuntimeExceptionDao(Activity.class);
        }

        return activityRuntimeDao;
    }

    public Dao<Activity, Long> getActivityDao() throws SQLException {
        if (activityDao == null) {
            activityDao = getDao(Activity.class);
        }

        return activityDao;
    }

    public RuntimeExceptionDao<Part, Long> getPartRuntimeDao() {
        if (partRuntimeDao== null) {
            partRuntimeDao= getRuntimeExceptionDao(Part.class);
        }

        return partRuntimeDao;
    }

    public Dao<Part, Long> getDao() throws SQLException {
        if (partDao== null) {
            partDao= getDao(Part.class);
        }

        return partDao;
    }

}