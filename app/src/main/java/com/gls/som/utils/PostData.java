package com.gls.som.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.gls.som.BaseActivity;


/**
 * Created by Santosh on 06-Oct-15.
 */
public class PostData extends AsyncTask {
    String url;
    String result = "";
    String callFor = "";
    String data = "";
    BaseActivity activity;
    ProgressDialog progressDialog;

    public PostData(String data, BaseActivity activity, String callFor){
        this.activity = activity;
        this.callFor = callFor;
        this.data = data;
        url = createURL(callFor);
    }

    private String createURL(String callFor) {
        url = Constants.BASE_URL;

        if(callFor==CallFor.LOGIN){
            url = url+URL.LOGIN;
        }
        if(callFor==CallFor.SAVEORDER){
            url = url+URL.SAVEORDER;
        }

        return url;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity,"","Loading...");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("URL ===>",url);
        Log.e("Data ===>",data);
        try {
            result = ServerConnection.giveResponse(url, data);
        } catch (Exception e){
            Log.e("Error doInBackground",e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        activity.onGetResponse(result,callFor);
    }
}
