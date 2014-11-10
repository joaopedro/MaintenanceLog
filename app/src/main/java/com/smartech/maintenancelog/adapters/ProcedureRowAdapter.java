package com.smartech.maintenancelog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.smartech.maintenancelog.R;
import com.smartech.maintenancelog.db.Activity;

import java.util.List;

/**
 * Created by jpedro on 21-09-2014.
 */

public class ProcedureRowAdapter extends ArrayAdapter<Activity> {

    private final Context context;
    private final List<Activity> itemsArrayList;

    public ProcedureRowAdapter(Context context, List<Activity> itemsArrayList) {

        super(context, R.layout.layout_procedure_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.layout_procedure_row, parent, false);

        rowView.setPadding(0,0,0,0);
        rowView.setBottom(0);

        TextView procedureText = (TextView) rowView.findViewById(R.id.procedure_text);
        CheckBox procedureCheck = (CheckBox) rowView.findViewById(R.id.procedure_checkBox);
        EditText procedureValue = (EditText) rowView.findViewById(R.id.procedure_value);


        // 4. Set the text for textView
        procedureText.setText(itemsArrayList.get(position).getDiscription());

        if(itemsArrayList.get(position).type.equals("t")){
            procedureCheck.setVisibility(View.GONE);
            procedureValue.setVisibility(View.GONE);
        }

        if(itemsArrayList.get(position).type.equals("c")){
            procedureCheck.setVisibility(View.VISIBLE);
            procedureValue.setVisibility(View.GONE);
        }

        if(itemsArrayList.get(position).type.equals("i")){
            procedureCheck.setVisibility(View.VISIBLE);
            procedureValue.setVisibility(View.VISIBLE);
        }

        // 5. retrn rowView
        return rowView;
    }

}
