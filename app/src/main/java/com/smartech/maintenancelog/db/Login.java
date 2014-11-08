package com.smartech.maintenancelog.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jpedro on 08-11-2014.
 */
public class Login {
    @DatabaseField(id = true) private Long id;
    @DatabaseField private String user;
    @DatabaseField private String password;
    @DatabaseField private String perfil;
    @DatabaseField private String name;
    @DatabaseField private String lastName;
    @DatabaseField private String tecNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTecNumber() {
        return tecNumber;
    }

    public void setTecNumber(String tecNumber) {
        this.tecNumber = tecNumber;
    }
}
