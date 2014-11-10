package com.smartech.maintenancelog;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.smartech.maintenancelog.db.DatabaseHelper;


/**
 * An activity representing a single Maintenance detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MaintenanceListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link MaintenanceDetailFragment}.
 */
public class MaintenanceDetailActivity extends OrmLiteBaseActivity<DatabaseHelper>
    implements View.OnClickListener{

    private boolean mTwoPane;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.maintenance_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            itemId = getIntent().getStringExtra(MaintenanceDetailFragment.ARG_ITEM_ID);
            mTwoPane = getIntent().getBooleanExtra(MaintenanceDetailFragment.TWO_PANE, false);

            Bundle arguments = new Bundle();

            if(getIntent().getStringExtra(MaintenanceDetailFragment.ARG_SCANNED_CODE)!=null){
                String scannedCode = getIntent().getStringExtra(MaintenanceDetailFragment.ARG_SCANNED_CODE);
                itemId = scannedCode.substring(scannedCode.indexOf("Número SAP: ")+12, scannedCode.indexOf("\r\nEquipamento"));
                arguments.putString(MaintenanceDetailFragment.ARG_SCANNED_CODE, scannedCode);
            }else{
                arguments.putString(MaintenanceDetailFragment.ARG_ITEM_ID, itemId);

            }


            MaintenanceDetailFragment fragment = new MaintenanceDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.maintenance_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, MaintenanceListActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.finalizar || view.getId() == R.id.standby){
            finish();
        }else if(view.getId() == R.id.lista_material) {
            Intent detailIntent = new Intent(this, MaintenanceParts.class);
            detailIntent.putExtra(MaintenanceParts.ARG_ITEM_ID, itemId);
            startActivity(detailIntent);
        }else{
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MaintenanceHist.class);
            detailIntent.putExtra(MaintenanceHist.MaintenanceHistFragment.ITEM_ID, itemId);
            startActivity(detailIntent);
        }
    }
}
