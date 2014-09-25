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
import com.smartech.maintenancelog.dummy.DummyContent;

import java.util.List;

/**
 * Created by jpedro on 21-09-2014.
 */

public class PartRowAdapter extends ArrayAdapter<DummyContent.Part> {

    private final Context context;
    private final List<DummyContent.Part> itemsArrayList;

    public PartRowAdapter(Context context, List<DummyContent.Part> itemsArrayList) {

        super(context, R.layout.layout_part_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.layout_part_row, parent, false);

        rowView.setPadding(0,0,0,0);
        rowView.setBottom(0);

        TextView partNameText = (TextView) rowView.findViewById(R.id.part_name);


        // 4. Set the text for textView
        partNameText.setText(itemsArrayList.get(position).name);

        // 5. retrn rowView
        return rowView;
    }

}
