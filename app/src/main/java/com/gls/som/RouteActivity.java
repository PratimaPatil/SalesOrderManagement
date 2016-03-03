package com.gls.som;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gls.som.route.RouteAdapter;
import com.gls.som.route.RouteResponse;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;
import com.google.gson.Gson;

public class RouteActivity extends BaseActivity {
    ListView routelistview;
    RouteResponse routeResponse=new RouteResponse();
    CoordinatorLayout main_Layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        routelistview = (ListView) findViewById(R.id.routelist);
        main_Layout= (CoordinatorLayout) findViewById(R.id.main_Layout);
        new GetData(this, CallFor.SHOWROUTE,"").execute();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onGetResponse(String result, String callFor) {
        Log.e("result", result);
        routeResponse=new Gson().fromJson(result, RouteResponse.class);
        if (callFor.equalsIgnoreCase(CallFor.SHOWROUTE))
        {
            if (routeResponse!=null) {
                if (routeResponse.getStatus().equalsIgnoreCase("success")) {
                    Log.e("status", routeResponse.getStatus());
                    RouteAdapter routeAdapter = new RouteAdapter(this, routeResponse.getListofRoute());
                    routelistview.setAdapter(routeAdapter);
                }else
                {
                    Snackbar snackbar = Snackbar
                            .make(main_Layout, routeResponse.getMessage(), Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                }
            }else
            {
                Snackbar snackbar = Snackbar
                        .make(main_Layout, "Server is not responding", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);
                snackbar.show();
            }

        }
    }
}
