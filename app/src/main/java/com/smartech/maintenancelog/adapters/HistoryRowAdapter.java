package com.smartech.maintenancelog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartech.maintenancelog.R;
import com.smartech.maintenancelog.dummy.DummyContent;

import java.util.List;
import java.util.Random;

public class HistoryRowAdapter extends ArrayAdapter<DummyContent.Maintenance> {

        private final Context context;
        private final String numEquipamento;
        private final List<DummyContent.Maintenance> itemsArrayList;

        public HistoryRowAdapter(Context context, List<DummyContent.Maintenance> itemsArrayList, String numEquipamento) {
 
            super(context, R.layout.layout_history_row, itemsArrayList);
 
            this.context = context;
            this.numEquipamento = numEquipamento;
            this.itemsArrayList = itemsArrayList;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            // 1. Create inflater 
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.layout_history_row, parent, false);

            TextView numeroDeEquipamento = (TextView) rowView.findViewById(R.id.num_equipamento_manutencao);
            TextView dataManutencao = (TextView) rowView.findViewById(R.id.data_manutencao);
            TextView tecnico = (TextView) rowView.findViewById(R.id.tecnico);

            // 4. Set the text for textView
            numeroDeEquipamento.setText(numEquipamento);
            dataManutencao.setText(itemsArrayList.get(position).date);
            tecnico.setText(itemsArrayList.get(position).tec);

            // 5. retrn rowView
            return rowView;
        }
}