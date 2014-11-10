package com.smartech.maintenancelog.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jpedro on 08-11-2014.
 */
public class NextOrdem {
    @DatabaseField(generatedId = true) private Long id;
    @DatabaseField private String date;
    @DatabaseField private String tec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTec() {
        return tec;
    }

    public void setTec(String tec) {
        this.tec = tec;
    }
}
