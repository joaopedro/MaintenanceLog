package com.smartech.maintenancelog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.smartech.maintenancelog.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;


public class MaintenanceHistory extends Activity {

    public static final String ARG_ITEM = "arg_item";
    private DummyContent.DummyItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_history);

        if(getIntent().getStringExtra(MaintenanceHistory.ARG_ITEM)!=null) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getIntent().getStringExtra(MaintenanceHistory.ARG_ITEM));
        }

        Spinner spinner = (Spinner) findViewById(R.id.maintenance_history_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        List<String> treatedMaintenanceHistory = new ArrayList<String>();

        for (DummyContent.Maintenance maintenance : mItem.history) {
            treatedMaintenanceHistory.add(mItem.numEquipamento + " | " + maintenance.date + " | "+maintenance.tec);
        }
        
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, treatedMaintenanceHistory);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        TextView nextMaintenance = (TextView) findViewById(R.id.next_maintenance);
        nextMaintenance.setText(mItem.numEquipamento + " | " + mItem.nextMaintenance.date + " | " + mItem.nextMaintenance.tec);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maintenance_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
