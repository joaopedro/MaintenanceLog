package com.smartech.maintenancelog.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jpedro on 08-11-2014.
 */
public class HistoryEntry {
    @DatabaseField(id = true) private Long id;
    @DatabaseField(foreign = true) private Equipamento equipamento;

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
}
