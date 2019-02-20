package com.example.saidahmed.rs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by said ahmed on 6/17/2017.
 */


public class Rdata extends AsyncTask<String, String, List<String>>{

    String data=null;
    TextView t1;
    List <String> prices=null;
    private static Context context;
    public Rdata(Context c) {
        context = c;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected void onProgressUpdate(String... values) {}

    @Override
    protected List<String> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        List<String> jsonStr = null;

        try {
           /* final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY_PARAM, params[0])
                    .appendQueryParameter(API_KEY_PARAM,APIKey)
                    .build();*/
            URL url = new URL(params[0]);
            //Log.d(TAG, builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            String finaljson=buffer.toString();
            JSONObject parentObj=new JSONObject(finaljson);
            JSONArray parentArray=parentObj.getJSONArray("products");
            for(int i=0;i<parentArray.length();i++) {
                JSONObject finalObj = parentArray.getJSONObject(i);
                prices.add(finalObj.getString("price"));
            }


        } catch (IOException e) {
           // Log.e(LOG_TAG, "Error ", e);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("0", "Error closing stream", e);
                }
            }
        }

        return prices;

    }
    @Override
    protected void onPostExecute(List<String> dat) {

       Toast.makeText(context,dat.toString(),
                Toast.LENGTH_LONG).show();
        /*AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
*/
       // data=dat.toString();
    }




}
