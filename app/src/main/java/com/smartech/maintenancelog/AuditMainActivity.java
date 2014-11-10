package com.smartech.maintenancelog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartech.maintenancelog.integration.zxing.IntentIntegrator;
import com.smartech.maintenancelog.integration.zxing.IntentResult;


public class AuditMainActivity extends Activity {

    private ProgressDialog progress;
    private TextView sucessText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_main);
        sucessText = (TextView) findViewById(R.id.sucess_text);
        progress = new ProgressDialog(this);

        final Button syncButton = (Button) findViewById(R.id.sync_audit);
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
                                    sucessText.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                    }
                };
                t.start();
            }
        });

        final Button scanCodeButton = (Button) findViewById(R.id.readCode_audit);
        scanCodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(AuditMainActivity.this);
                integrator.initiateScan();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Intent detailIntent = new Intent(this, MaintenanceDetailActivity.class);
            detailIntent.putExtra(MaintenanceDetailFragment.ARG_SCANNED_CODE, scanResult.getContents());
            startActivity(detailIntent);        }
        // else continue with any other code you need in the method

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.audit_main, menu);
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
