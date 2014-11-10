package com.smartech.maintenancelog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartech.maintenancelog.R;
import com.smartech.maintenancelog.db.Ordem;

import java.util.List;

public class MaintenanceRowAdapter extends ArrayAdapter<Ordem> {

    private final Context context;
    private final List<Ordem> itemsArrayList;

    public MaintenanceRowAdapter(Context context, List<Ordem> itemsArrayList) {

        super(context, R.layout.layout_maintenance_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.layout_maintenance_row, parent, false);

        rowView.setPadding(0,0,0,0);
        rowView.setBottom(0);
        rowView.setPaddingRelative(0,0,0,0);

        TextView numeroDeOrdem = (TextView) rowView.findViewById(R.id.num_ordem);
        TextView numeroDeEquipamento = (TextView) rowView.findViewById(R.id.enum_equipamento);
        TextView designacaoEquipamento = (TextView) rowView.findViewById(R.id.designacao_equipamento);
        TextView localizacao = (TextView) rowView.findViewById(R.id.localizacao_equipamento);
        TextView periodicidade = (TextView) rowView.findViewById(R.id.perioridade_ordem);
        TextView tipo = (TextView) rowView.findViewById(R.id.tipo_ordem);

        // 4. Set the text for textView
        numeroDeOrdem.setText(itemsArrayList.get(position).getOrderNumber());
        numeroDeEquipamento.setText(itemsArrayList.get(position).getEquipament().getNumber());
        designacaoEquipamento.setText(itemsArrayList.get(position).getEquipament().getName());
        localizacao.setText(itemsArrayList.get(position).getEquipament().getLocalizacao());
        periodicidade.setText(itemsArrayList.get(position).getPeriodicity());
        tipo.setText(itemsArrayList.get(position).getType());

        if(tipo.getText().equals("PM01")){
            rowView.setBackgroundColor(Color.RED);
        }
        // 5. retrn rowView
        return rowView;
    }
}