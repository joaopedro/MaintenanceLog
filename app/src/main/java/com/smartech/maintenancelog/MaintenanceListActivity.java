package com.smartech.maintenancelog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.smartech.maintenancelog.adapters.MaintenanceRowAdapter;
import com.smartech.maintenancelog.db.Activity;
import com.smartech.maintenancelog.db.DatabaseHelper;
import com.smartech.maintenancelog.db.HistoryEntry;
import com.smartech.maintenancelog.db.Login;
import com.smartech.maintenancelog.db.Ordem;
import com.smartech.maintenancelog.db.Part;
import com.smartech.maintenancelog.integration.zxing.IntentIntegrator;
import com.smartech.maintenancelog.integration.zxing.IntentResult;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * An activity representing a list of Maintenances. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MaintenanceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MaintenanceListFragment} and the item details
 * (if present) is a {@link MaintenanceDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link MaintenanceListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MaintenanceListActivity extends OrmLiteBaseActivity<DatabaseHelper>
implements MaintenanceListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_list);

        ListView lv = (ListView)findViewById(android.R.id.list);
        lv.setDivider(null);

        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        lv.setEmptyView(emptyText);

        progress = new ProgressDialog(this);

        if (findViewById(R.id.maintenance_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MaintenanceListFragment) getFragmentManager()
                    .findFragmentById(R.id.maintenance_list))
                    .setActivateOnItemClick(true);
        }

        final Button syncButton = (Button) findViewById(R.id.sync);
        syncButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progress.setMessage("Reloading data... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();

                new SyncOrdemTask(((MaintenanceLogApp)getApplicationContext()).getLoggedUser()).execute((Void) null);
            }
        });

        final Button scanCodeButton = (Button) findViewById(R.id.readCode);
        scanCodeButton.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
                  IntentIntegrator integrator = new IntentIntegrator(MaintenanceListActivity.this);
                  integrator.initiateScan();
              }
          });
        // TODO: If exposing deep links into your app, handle intents here.
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult.getContents() != null) {
            Intent detailIntent = new Intent(this, MaintenanceDetailActivity.class);
            detailIntent.putExtra(MaintenanceDetailFragment.ARG_SCANNED_CODE, scanResult.getContents());
            startActivity(detailIntent);        }
        // else continue with any other code you need in the method

    }

    /**
     * Callback method from {@link MaintenanceListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Ordem ordem) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(MaintenanceDetailFragment.ARG_ITEM_ID, ordem.getId());
            arguments.putBoolean(MaintenanceDetailFragment.TWO_PANE, mTwoPane);
            MaintenanceDetailFragment fragment = new MaintenanceDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.maintenance_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MaintenanceDetailActivity.class);
            detailIntent.putExtra(MaintenanceDetailFragment.ARG_ITEM_ID, ""+ordem.getId());
            detailIntent.putExtra(MaintenanceDetailFragment.TWO_PANE, mTwoPane);
            startActivity(detailIntent);
        }
    }

    public class SyncOrdemTask extends AsyncTask<Void, Void, String> {

        private final Login login;

        public SyncOrdemTask(Login login) {
            this.login = login;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "ok";

            try {
                populateOrdensDatabase(getOrdens());
            } catch (IOException e) {
                Log.i("Ordens", "Unable to get ordens from server!");
                result = "fail";
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MaintenanceRowAdapter maintenanceRowAdapter = ((MaintenanceListFragment) getFragmentManager()
                            .findFragmentById(R.id.maintenance_list)).dummyItemArrayAdapter;
                    maintenanceRowAdapter.clear();
                    maintenanceRowAdapter.addAll(getHelper().getOrdemRuntimeDao().queryForEq("tecNumber", login.getTecNumber()));
                }
            });

            return result;
        }
        @Override
        protected void onPostExecute(final String perfil) {
            // close the progress bar dialog
            progress.dismiss();
        }
        private String getOrdens() throws IOException {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            response = httpclient.execute(new HttpGet("http://176.111.107.200:8080/ordem/tecnico/"+login.getTecNumber()));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

            return responseString;
        }

        private void populateOrdensDatabase(String json){
            Gson gson = new Gson();
            List<Ordem> ordens = gson.fromJson(json, new TypeToken<List<Ordem>>(){}.getType());

            for (Ordem ordem : ordens) {
                if(!getHelper().getOrdemRuntimeDao().idExists(ordem.getId())){
                    if(ordem.getEquipament().getNextHistoryEntry()!=null){
                        getHelper().getNextOrdemRuntimeDao().create(ordem.getEquipament().getNextHistoryEntry());
                    }
                    getHelper().getOrdemRuntimeDao().create(ordem);
                    if(!getHelper().getEquipamentoRuntimeDao().idExists(ordem.getEquipament().getId())) {
                        getHelper().getEquipamentoRuntimeDao().create(ordem.getEquipament());
                    }

                    if(ordem.getTransientActivities()!=null){
                        for (Activity activity : ordem.getTransientActivities()) {
                            if(!getHelper().getActivityRuntimeDao().idExists(activity.getId())) {
                                activity.setOrdem(ordem);
                                getHelper().getActivityRuntimeDao().create(activity);
                            }

                        }
                    }
                    if(ordem.getTransientParts()!=null){
                        for (Part part : ordem.getTransientParts()) {
                            if(!getHelper().getPartRuntimeDao().idExists(part.getId())) {
                                part.setOrdem(ordem);
                                getHelper().getPartRuntimeDao().create(part);
                            }

                        }
                    }
                    if(ordem.getEquipament().getTransientHistory()!=null){
                        getHelper().getHistoryRuntimeExceptionDao().delete(ordem.getEquipament().getmHistoryEntries());
                        for (HistoryEntry historyEntry : ordem.getEquipament().getTransientHistory()) {
                            historyEntry.setEquipamento(ordem.getEquipament());
                            getHelper().getHistoryRuntimeExceptionDao().create(historyEntry);
                        }
                    }

                }
            }
        }
    };
}
