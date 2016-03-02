package com.gls.som;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gls.som.utils.AppData;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends BaseActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=HomeActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert.setMessage("Are you sure exit from application?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("exit", "app EXIT");
                dialogInterface.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alert.show();
    }

    @Override
    public void onGetResponse(String result, String callFor) {
        if (callFor.equalsIgnoreCase(CallFor.HOME))
        {
            Log.e("callFor",callFor);
            Log.e("result",result);

            JSONObject json = null,json2=null,json3=null;    // create JSON obj from string
            String isorg="",status="";
            int pageSize;
            try {
                json = new JSONObject(result);
                status= (String) json.get("status");
                json3=json.getJSONObject("salesPersonBean");
                isorg=json3.getString("is_organised");
                pageSize=json3.getInt("pageSize");
                AppData.setPageSize(context,pageSize+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (json!=null) {
                if (status.equalsIgnoreCase("success")) {
                    if (isorg.equalsIgnoreCase("y")) {
                        Intent showRouteActivity = new Intent(HomeActivity.this, RouteActivity.class);
                        startActivity(showRouteActivity);

                    } else {

                    }
                }
            }else
            {
                Toast.makeText(getApplicationContext(),"Server is not responding",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void gotoCreateOrder(View view)
    {
        new GetData(this,CallFor.HOME,"").execute();
    }
}
