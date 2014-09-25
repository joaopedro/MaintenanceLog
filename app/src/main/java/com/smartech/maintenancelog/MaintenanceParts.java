package com.smartech.maintenancelog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.smartech.maintenancelog.adapters.PartRowAdapter;
import com.smartech.maintenancelog.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;


public class MaintenanceParts extends Activity implements View.OnClickListener{

    public static final String ARG_ITEM_ID = "item_id";
    private String itemId;
    private DummyContent.DummyItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_parts);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lista de Material");

        itemId = getIntent().getStringExtra(MaintenanceParts.ARG_ITEM_ID);
        mItem = DummyContent.ITEM_MAP.get(itemId);
        ListView parstList = (ListView) findViewById(R.id.maintenance_parts_list);

        PartRowAdapter adapter = new PartRowAdapter(this,mItem.parts);
// Apply the adapter to the spinner
        parstList.setAdapter(adapter);

        Button finalize_button = (Button) findViewById(R.id.finalize_button);
        finalize_button.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maintenance_parts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
