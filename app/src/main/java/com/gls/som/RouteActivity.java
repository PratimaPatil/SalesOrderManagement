package com.gls.som;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.gls.som.route.RouteAdapter;
import com.gls.som.route.RouteResponse;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;
import com.google.gson.Gson;

public class RouteActivity extends BaseActivity {
    ListView routelistview;
    RouteResponse routeResponse=new RouteResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        routelistview = (ListView) findViewById(R.id.routelist);
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
                }
            }else
            {
                Toast.makeText(getApplicationContext(),"Server is not responding",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
