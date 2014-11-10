package com.smartech.maintenancelog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.smartech.maintenancelog.adapters.HistoryRowAdapter;
import com.smartech.maintenancelog.db.DatabaseHelper;
import com.smartech.maintenancelog.db.HistoryEntry;
import com.smartech.maintenancelog.db.Ordem;

import java.util.ArrayList;


public class MaintenanceHist extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_hist);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(MaintenanceHistFragment.ITEM_ID,
                    getIntent().getStringExtra(MaintenanceHistFragment.ITEM_ID));

            MaintenanceHistFragment fragment = new MaintenanceHistFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .add(R.id.maintenance_history_container, fragment)
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maintenance_hist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MaintenanceHistFragment extends Fragment {

        public static final String ITEM_ID = "item_id";
        private Ordem mItem;

        public MaintenanceHistFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments().containsKey(ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                mItem =((MaintenanceHist)getActivity()).getHelper().getOrdemRuntimeDao().queryForId(getArguments().getLong(ITEM_ID));
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maintenance_hist, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.maintenance_history_list);

            HistoryRowAdapter adapter = new HistoryRowAdapter(getActivity(), new ArrayList<HistoryEntry>(mItem.getEquipament().getHistoryEntries()),
                    mItem.getEquipament().getNumber());
// Apply the adapter to the spinner
            listView.setAdapter(adapter);

            TextView nextMaintenance = (TextView) rootView.findViewById(R.id.next_maintenance);
            nextMaintenance.setText(mItem.getEquipament().getNumber() + " | " + mItem.getNextMaintenanceDate() + " | " + mItem.getNextMaintenanceTec());
            return rootView;
        }
    }
}
