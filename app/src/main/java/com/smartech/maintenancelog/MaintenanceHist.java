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
import com.smartech.maintenancelog.db.AuditDatabaseHelper;
import com.smartech.maintenancelog.db.DatabaseHelper;
import com.smartech.maintenancelog.db.Equipamento;
import com.smartech.maintenancelog.db.HistoryEntry;
import com.smartech.maintenancelog.db.Ordem;

import java.util.ArrayList;


public class MaintenanceHist extends OrmLiteBaseActivity<DatabaseHelper> {

    private AuditDatabaseHelper auditHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_hist);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            if(getIntent().getStringExtra(MaintenanceHistFragment.ITEM_ID) !=null){
                arguments.putString(MaintenanceHistFragment.ITEM_ID,
                        getIntent().getStringExtra(MaintenanceHistFragment.ITEM_ID));
            }
            if(getIntent().getStringExtra(MaintenanceHistFragment.ITEM_EQUIPAMENTO) !=null) {
                arguments.putString(MaintenanceHistFragment.ITEM_EQUIPAMENTO,
                        getIntent().getStringExtra(MaintenanceHistFragment.ITEM_EQUIPAMENTO));
            }
            MaintenanceHistFragment fragment = new MaintenanceHistFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .add(R.id.maintenance_history_container, fragment)
                    .commit();
        }

    }
    private AuditDatabaseHelper getAuditHelper() {
        if (auditHelper == null) {
            auditHelper = new AuditDatabaseHelper(this);
        }
        return auditHelper;
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
        public static final String ITEM_EQUIPAMENTO = "num_equipamento";
        private Ordem mItem;
        private Equipamento mEquipamento;

        public MaintenanceHistFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments().containsKey(ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                String s_item_id = getArguments().getString(ITEM_ID);
                mItem = ((MaintenanceHist) getActivity()).getHelper().getOrdemRuntimeDao().queryForId(Long.valueOf(s_item_id));
            }
            if (getArguments().containsKey(ITEM_EQUIPAMENTO)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                String s_equipamento_id = getArguments().getString(ITEM_EQUIPAMENTO);
                mEquipamento = ((MaintenanceHist) getActivity()).getAuditHelper().getEquipamentoRuntimeDao().queryForEq("number",s_equipamento_id).get(0);
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maintenance_hist, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.maintenance_history_list);
            if(mItem!=null){
                fillFromOrder(rootView, listView);
            }else if(mEquipamento!=null){
                fillFromEquipamento(rootView, listView);
            }
            return rootView;
        }

        private void fillFromOrder(View rootView, ListView listView) {
            HistoryRowAdapter adapter = new HistoryRowAdapter(getActivity(), new ArrayList<HistoryEntry>(mItem.getEquipament().getmHistoryEntries()),
                    mItem.getEquipament().getNumber());
// Apply the adapter to the spinner
            listView.setAdapter(adapter);

            TextView nextMaintenance = (TextView) rootView.findViewById(R.id.next_maintenance);
            if(mItem.getEquipament().getNextHistoryEntry() != null){
                nextMaintenance.setText(mItem.getEquipament().getNumber() + " | " + mItem.getEquipament().getNextHistoryEntry().getDate()
                        + " | " + mItem.getEquipament().getNextHistoryEntry().getTec());
            }else {
                nextMaintenance.setText("N/A");
            }
        }
        private void fillFromEquipamento(View rootView, ListView listView) {
            HistoryRowAdapter adapter = new HistoryRowAdapter(getActivity(), new ArrayList<HistoryEntry>(mEquipamento.getmHistoryEntries()),
                    mEquipamento.getNumber());
// Apply the adapter to the spinner
            listView.setAdapter(adapter);

            TextView nextMaintenance = (TextView) rootView.findViewById(R.id.next_maintenance);
            if(mEquipamento.getNextHistoryEntry() != null){
                nextMaintenance.setText(mEquipamento.getNumber() + " | " + mEquipamento.getNextHistoryEntry().getDate()
                        + " | " + mEquipamento.getNextHistoryEntry().getTec());
            }else {
                nextMaintenance.setText("N/A");
            }
        }

    }
}
