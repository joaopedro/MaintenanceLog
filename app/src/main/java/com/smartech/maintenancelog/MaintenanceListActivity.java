package com.smartech.maintenancelog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smartech.maintenancelog.dummy.DummyContent;
import com.smartech.maintenancelog.integration.zxing.IntentIntegrator;
import com.smartech.maintenancelog.integration.zxing.IntentResult;

import java.util.ArrayList;
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
public class MaintenanceListActivity extends Activity
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
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(false);
                progress.show();

                final int totalProgressTime = 100;

                final Thread t = new Thread(){

                    @Override
                    public void run(){

                        int jumpTime = 0;
                        while(jumpTime < totalProgressTime){
                            try {
                                sleep(200);
                                jumpTime += 5;
                                progress.setProgress(jumpTime);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }

                        // ok, file is downloaded,
                        if (jumpTime >= 100) {

                            // sleep 2 seconds, so that you can see the 100%
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // close the progress bar dialog
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<DummyContent.DummyItem> dummyItemArrayAdapter = ((MaintenanceListFragment) getFragmentManager()
                                            .findFragmentById(R.id.maintenance_list)).dummyItemArrayAdapter;
                                    if(DummyContent.ITEMS.size()==0){
                                        List<DummyContent.Procedure> procedureOne = new ArrayList<DummyContent.Procedure>();
                                        procedureOne.add(new DummyContent.Procedure("CHECK", "Verificar Pressão do sensor X"));
                                        procedureOne.add(new DummyContent.Procedure("CHECK", "Verificar Pressão do sensor Y"));
                                        procedureOne.add(new DummyContent.Procedure("INFO", "Ter atenção ao sentido da Rosca"));
                                        procedureOne.add(new DummyContent.Procedure("CHECK", "Verificar as afinações"));
                                        procedureOne.add(new DummyContent.Procedure("INPUT", "Recolher valor WIS"));

                                        List<DummyContent.Maintenance> maintenanceHistory = new ArrayList<DummyContent.Maintenance>();
                                        maintenanceHistory.add(new DummyContent.Maintenance("01-03-2013", "João Pedro"));
                                        maintenanceHistory.add(new DummyContent.Maintenance("12-05-2013", "António Castro"));
                                        maintenanceHistory.add(new DummyContent.Maintenance("20-11-2013", "Manuel Almeida"));
                                        maintenanceHistory.add(new DummyContent.Maintenance("03-02-2014", "João Pedro"));
                                        maintenanceHistory.add(new DummyContent.Maintenance("05-05-2014", "Manuel Almeida"));
                                        maintenanceHistory.add(new DummyContent.Maintenance("14-07-2014", "Manuel Almeida"));

                                        DummyContent.Maintenance nextMaintenance = new DummyContent.Maintenance("24-10-2014", "João Pedro");

                                        DummyContent.addItem(new DummyContent.DummyItem("12331", "12331", "98492", "Ar Condicionado","Leiria", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("122", "122", "23921", "Aspirador Indústrial","Lisboa", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("9872", "9872", "884351", "Compressor de 200 L", "Faro", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("2214", "2214", "660098", "Chiller da UA 12","Coimbra", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("50021653", "981", "50021653", "Unidade X da Linha 2","Lisboa", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("2213", "2213", "412104", "Ar Condicionado","Marinha Grande", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("215", "215", "909042","Elevador Hidraúlico","Leiria", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("12331", "12331", "98492", "Ar Condicionado","Leiria", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("122", "122", "23921", "Aspirador Indústrial","Lisboa", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("9872", "9872", "884351", "Compressor de 200 L", "Faro", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("2214", "2214", "660098", "Chiller da UA 12","Coimbra", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("981", "981", "1221114", "Unidade X da Linha 2","Lisboa", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("2213", "2213", "412104", "Ar Condicionado","Marinha Grande", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                        DummyContent.addItem(new DummyContent.DummyItem("215", "215", "909042","Elevador Hidraúlico","Leiria", "N/A", procedureOne,maintenanceHistory, nextMaintenance));
                                    }
                                    dummyItemArrayAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                    }
                };
                t.start();
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
        if (scanResult != null) {
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
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(MaintenanceDetailFragment.ARG_ITEM_ID, id);
            MaintenanceDetailFragment fragment = new MaintenanceDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.maintenance_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MaintenanceDetailActivity.class);
            detailIntent.putExtra(MaintenanceDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
