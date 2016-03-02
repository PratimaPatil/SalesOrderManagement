package com.gls.som.route;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gls.som.R;
import com.gls.som.RetailerActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by pratima on 25/2/16.
 */
public class RouteAdapter extends BaseAdapter {
    Context context;
    ArrayList<RouteBean> list;
    public RouteAdapter(Context context,ArrayList<RouteBean> list) {
        super();
        Log.e("route","adapter");
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.route_layout, null);
        ((TextView) convertView.findViewById(R.id.day_name)).setText(list.get(position).getDay_name());
        ((TextView) convertView.findViewById(R.id.tag_name)).setText(list.get(position).getTag_name());
        convertView.setTag(list.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteBean routeBean=new RouteBean();
                routeBean= (RouteBean) v.getTag();
                Log.e("routeBean", new Gson().toJson(routeBean));
                Intent retailer=new Intent(context, RetailerActivity.class);
                retailer.putExtra("day_id",routeBean.getDay_id());
                context.startActivity(retailer);

            }
        });
        return convertView;
    }



}
