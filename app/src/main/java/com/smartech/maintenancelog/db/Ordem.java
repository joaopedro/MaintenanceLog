package com.smartech.maintenancelog.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by jpedro on 08-11-2014.
 */
public class Ordem {
    @DatabaseField(id = true) private Long id;
    @DatabaseField private String orderNumber;
    @DatabaseField private String periodicity;
    @DatabaseField private String type;
    @DatabaseField private String state;
    @DatabaseField private String tecNumber;
    @DatabaseField private String obs;
    @DatabaseField(foreign = true, foreignAutoRefresh=true, canBeNull = false) private Equipamento equipament;
    private Collection<Activity> activities;
    private Collection<Part> parts;
    @ForeignCollectionField(eager = false) private ForeignCollection<Activity> mActivities;
    @ForeignCollectionField(eager = false) private ForeignCollection<Part> mParts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTecNumber() {
        return tecNumber;
    }

    public void setTecNumber(String tecNumber) {
        this.tecNumber = tecNumber;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Equipamento getEquipament() {
        return equipament;
    }

    public void setEquipament(Equipamento equipament) {
        this.equipament = equipament;
    }

    public void setActivities(Collection<Activity> activities) {
        this.activities = activities;
    }


    public void setParts(Collection<Part> parts) {
        this.parts = parts;
    }

    public ForeignCollection<Activity> getmActivities() {
        return mActivities;
    }

    public void setmActivities(ForeignCollection<Activity> mActivities) {
        this.mActivities = mActivities;
    }

    public ForeignCollection<Part> getmParts() {
        return mParts;
    }

    public void setmParts(ForeignCollection<Part> mParts) {
        this.mParts = mParts;
    }

    public Collection<Activity> getTransientActivities() {
        return activities;
    }

    public Collection<Part> getTransientParts() {
        return parts;
    }
}
