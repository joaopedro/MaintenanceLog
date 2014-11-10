package com.smartech.maintenancelog;

import android.app.Application;

import com.smartech.maintenancelog.db.Login;

/**
 * Created by jpedro on 09-11-2014.
 */
public class MaintenanceLogApp extends Application {
    private Login loggedUser;

    public Login getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Login loggedUser) {
        this.loggedUser = loggedUser;
    }
}
