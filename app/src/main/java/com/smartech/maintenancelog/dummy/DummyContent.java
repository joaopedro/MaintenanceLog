package com.smartech.maintenancelog.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
//        addItem(new DummyItem("1", "Item 1"));
//        addItem(new DummyItem("2", "Item 2"));
//        addItem(new DummyItem("3", "Item 3"));
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String numOrdem;
        public String numEquipamento;
        public String designacaoEquipamento;
        public String localizacao;
        public String periodicidade;
        public List<Procedure> procedures;
        public List<Maintenance> history;
        public Maintenance nextMaintenance;


        public DummyItem(String id, String numOrdem, String numEquipamento, String designacaoEquipamento, String localizacao, String periodicidade, List<Procedure> procedures,
                         List<Maintenance> maintenancesHistory, Maintenance nextMaintenance) {
            this.id = id;
            this.numOrdem = numOrdem;
            this.numEquipamento = numEquipamento;
            this.designacaoEquipamento = designacaoEquipamento;
            this.localizacao = localizacao;
            this.periodicidade = periodicidade;
            this.procedures = procedures;
            this.history = maintenancesHistory;
            this.nextMaintenance = nextMaintenance;
        }

        @Override
        public String toString() {
            return numOrdem;
        }

        public List<Procedure> getProcedures() {
            return procedures;
        }

        public void setProcedures(List<Procedure> procedures) {
            this.procedures = procedures;
        }
    }

    public static class Procedure {
        public String type;
        public String text;
        public Procedure(String type, String text){
            this.type = type;
            this.text = text;
        }
    }

    public static class Maintenance {
        public String date;
        public String tec;

        public Maintenance(String date,String tec){
            this.date = date;
            this.tec = tec;
        }

    }
}
