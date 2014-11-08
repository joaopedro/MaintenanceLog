package com.smartech.maintenancelog.api;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartech.maintenancelog.db.DatabaseHelper;
import com.smartech.maintenancelog.db.Login;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SyncLoginsTask extends AsyncTask<Void, String, String> {

    private Context mContext;

    public SyncLoginsTask(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet("http://176.111.107.200:8080/login"));
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
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Gson gson = new Gson();
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);

        List<Login> logins = gson.fromJson(result, new TypeToken<List<Login>>(){}.getType());

        for (Login login : logins) {
            if(!dbHelper.getSimpleDataDao().idExists(login.getId())){
                dbHelper.getSimpleDataDao().create(login);
            }
        }
    }
}