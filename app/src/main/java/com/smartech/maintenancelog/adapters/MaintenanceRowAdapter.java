package com.smartech.maintenancelog.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartech.maintenancelog.R;
import com.smartech.maintenancelog.dummy.DummyContent;

public class MaintenanceRowAdapter extends ArrayAdapter<DummyContent.DummyItem> {
 
        private final Context context;
        private final List<DummyContent.DummyItem> itemsArrayList;
 
        public MaintenanceRowAdapter(Context context, List<DummyContent.DummyItem> itemsArrayList) {
 
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

            // 4. Set the text for textView
            numeroDeOrdem.setText(itemsArrayList.get(position).numOrdem);
            numeroDeEquipamento.setText(itemsArrayList.get(position).numEquipamento);
            designacaoEquipamento.setText(itemsArrayList.get(position).designacaoEquipamento);
            localizacao.setText(itemsArrayList.get(position).localizacao);


            int anInt = randInt(1,4);

            if(anInt==1) periodicidade.setText("D");
            if(anInt==2) periodicidade.setText("S");
            if(anInt==3) periodicidade.setText("M");
            if(anInt==4) periodicidade.setText("A");

            // 5. retrn rowView
            return rowView;
        }
   private int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}