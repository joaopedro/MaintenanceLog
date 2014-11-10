package com.smartech.maintenancelog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class AuditDatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "maintenancelog_audit.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;


    // the DAO object we use to access the Equipamento table
    private Dao<Equipamento, Long> equipamentoDao = null;
    private RuntimeExceptionDao<Equipamento, Long> equipamentoRuntimeDao = null;
    private RuntimeExceptionDao<HistoryEntry, Long> historyRuntimeExceptionDao = null;
    private Dao<HistoryEntry, Long> historyDao = null;
    private RuntimeExceptionDao<NextOrdem, Long> nextOrdemRuntimeDao = null;
    private Dao<NextOrdem, Long> nextOrdemDao = null;

    public AuditDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(AuditDatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, HistoryEntry.class);
            TableUtils.createTable(connectionSource, Equipamento.class);
            TableUtils.createTable(connectionSource, NextOrdem.class);
        } catch (SQLException e) {
            Log.e(AuditDatabaseHelper.class.getName(), "Can't create database", e);
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
            Log.i(AuditDatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Equipamento.class, true);
            TableUtils.dropTable(connectionSource, HistoryEntry.class, true);
            TableUtils.dropTable(connectionSource, NextOrdem.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(AuditDatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
    public void clearDatabase() {
        try {
            Log.i(AuditDatabaseHelper.class.getName(), "clearing database");
            TableUtils.clearTable(connectionSource, Equipamento.class);
            TableUtils.clearTable(connectionSource, HistoryEntry.class);
            TableUtils.clearTable(connectionSource, NextOrdem.class);
        } catch (SQLException e) {
            Log.e(AuditDatabaseHelper.class.getName(), "Can't delete tables", e);
            throw new RuntimeException(e);
        }
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

    public Dao<NextOrdem, Long> getNextOrdemDao() throws SQLException {
        if (nextOrdemDao== null) {
            nextOrdemDao= getDao(NextOrdem.class);
        }
        return nextOrdemDao;
    }

    public RuntimeExceptionDao<NextOrdem, Long> getNextOrdemRuntimeDao() {
        if (nextOrdemRuntimeDao== null) {
            nextOrdemRuntimeDao= getRuntimeExceptionDao(NextOrdem.class);
        }
        return nextOrdemRuntimeDao;
    }

    public Dao<HistoryEntry, Long> getHistoryDao() throws SQLException {
        if (historyDao== null) {
            historyDao= getDao(HistoryEntry.class);
        }
        return historyDao;
    }

    public RuntimeExceptionDao<HistoryEntry, Long> getHistoryRuntimeExceptionDao() {
        if (historyRuntimeExceptionDao== null) {
            historyRuntimeExceptionDao= getRuntimeExceptionDao(HistoryEntry.class);
        }
        return historyRuntimeExceptionDao;
    }
}