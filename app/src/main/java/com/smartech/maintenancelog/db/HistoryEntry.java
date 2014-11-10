package com.smartech.maintenancelog.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jpedro on 08-11-2014.
 */
public class HistoryEntry {
    @DatabaseField(id = true) private Long id;
    @DatabaseField(foreign = true) private Equipamento equipamento;
    @DatabaseField private String date;
    @DatabaseField private String tecnico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }
}
