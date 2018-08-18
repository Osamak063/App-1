package com.ziaetaiba.islamicwebsite.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ziaetaiba.islamicwebsite.callbacks.DetailDataCallbacks;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetDetailJsonData extends AsyncTask<String, String, String> {

    private DetailDataCallbacks mCallbacks;

    private HttpURLConnection urlConnection;
    private String url ;
    private int serviceId ;
    private boolean errorFlag;

    public GetDetailJsonData(DetailDataCallbacks mCallbacks, String url, int serviceId) {

        this.mCallbacks = mCallbacks;
        this.url = url ;
        this.serviceId = serviceId ;
        errorFlag = false ;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        InputStream inputStream = null ;

        try {

            URL address = new URL(url);
            urlConnection = (HttpURLConnection) address.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch( Exception e) {

            errorFlag = true;
            result = new StringBuilder("") ;
            Log.e("GetDetailJsonData", "Exception: " + e.toString());
        }
        finally {

            try {

                if (inputStream != null)
                    inputStream.close();

            } catch (IOException e) {
                // do nothing...
            }

            if (urlConnection != null)
                urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        if (mCallbacks != null) {
            mCallbacks.onDetailPostUpdate(result, serviceId, errorFlag);
        }

        Log.e("GetDetailJsonData", "Result: " + result);
        //Do something with the JSON string
    }

}