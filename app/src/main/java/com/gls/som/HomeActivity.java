package com.gls.som;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gls.som.utils.AppData;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends BaseActivity {
    Context context;
    RelativeLayout ll_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      try {
          String code = getIntent().getStringExtra("EXIT");
          if(code.equals("yes")){
              Log.e("finish activity","");
              this.finish();
              return;
          }
      }catch (Exception e)
      {}



        setContentView(R.layout.activity_home);
        context=HomeActivity.this;
        ll_home= (RelativeLayout) findViewById(R.id.ll_home);
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

                Intent exitApp = new Intent(getApplicationContext(), HomeActivity.class);
                exitApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exitApp.putExtra("EXIT", "yes");
                startActivity(exitApp);
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
                        Snackbar snackbar = Snackbar
                                .make(ll_home, "Something went wrong", Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);

                        snackbar.show();
                    }
                }
            }else
            {
                Snackbar snackbar = Snackbar
                        .make(ll_home, "Server is not responding", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);

                snackbar.show();
            }

        }
    }
    public void gotoCreateOrder(View view)
    {
        new GetData(this,CallFor.HOME,"").execute();
    }
}
