package com.smartech.maintenancelog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.smartech.maintenancelog.db.AuditDatabaseHelper;
import com.smartech.maintenancelog.db.Equipamento;
import com.smartech.maintenancelog.db.HistoryEntry;
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


public class AuditMainActivity extends Activity {

    private ProgressDialog progress;
    private TextView sucessText;
    private AuditDatabaseHelper auditDatabaseHelper;

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
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();

                new SyncEquipamentosAuditTask().execute((Void) null);


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
    private AuditDatabaseHelper getHelper() {
        if (auditDatabaseHelper == null) {
            auditDatabaseHelper = new AuditDatabaseHelper(this);
        }
        return auditDatabaseHelper;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String equipNumber =scanResult.getContents().substring(scanResult.getContents().indexOf("Número SAP: ")+12,
                    scanResult.getContents().indexOf("\r\nEquipamento"));
            Intent detailIntent = new Intent(this, MaintenanceHist.class);
            detailIntent.putExtra(MaintenanceHist.MaintenanceHistFragment.ITEM_EQUIPAMENTO, equipNumber);
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
    public class SyncEquipamentosAuditTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result = "ok";

            try {
                populateOrdensDatabase(getOrdens());
            } catch (IOException e) {
                Log.i("Ordens", "Unable to get ordens from server!");
                result = "fail";
            }

            return result;
        }
        @Override
        protected void onPostExecute(final String perfil) {
            // close the progress bar dialog
            progress.dismiss();
            sucessText.setVisibility(View.VISIBLE);
        }
        private String getOrdens() throws IOException {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            response = httpclient.execute(new HttpGet("http://176.111.107.200:8080/equipamento"));
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
            getHelper().clearDatabase();
            Gson gson = new Gson();
            List<Equipamento> equipamentos = gson.fromJson(json, new TypeToken<List<Equipamento>>(){}.getType());

            for (Equipamento equipamento : equipamentos) {
                if(equipamento.getNextHistoryEntry()!=null){
                    getHelper().getNextOrdemRuntimeDao().create(equipamento.getNextHistoryEntry());
                }
                getHelper().getEquipamentoRuntimeDao().create(equipamento);
                if(equipamento.getTransientHistory()!=null){
                    for (HistoryEntry historyEntry : equipamento.getTransientHistory()) {
                        historyEntry.setEquipamento(equipamento);
                        getHelper().getHistoryRuntimeExceptionDao().create(historyEntry);
                    }
                }

            }
        }
    };

}
