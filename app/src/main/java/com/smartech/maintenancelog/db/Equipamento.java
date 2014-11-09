package com.smartech.maintenancelog.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Collection;

/**
 * Created by jpedro on 08-11-2014.
 */
public class Equipamento {
    @DatabaseField(id = true) private Long id;
    @DatabaseField private String number;
    @DatabaseField private String name;
    @DatabaseField private String localizacao;
    @ForeignCollectionField private Collection<HistoryEntry> historyEntries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Collection<HistoryEntry> getHistoryEntries() {
        return historyEntries;
    }

    public void setHistoryEntries(Collection<HistoryEntry> historyEntries) {
        this.historyEntries = historyEntries;
    }
}
