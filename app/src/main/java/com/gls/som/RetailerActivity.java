package com.gls.som;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gls.som.database.RetailerHelper;
import com.gls.som.retailer.RetailerBean;
import com.gls.som.retailer.RetailerResponse;
import com.gls.som.utils.AppData;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RetailerActivity extends BaseActivity {
    String day_id;
    RetailerResponse retailerResponse;
    ArrayList<RetailerBean> retailerBeans=new ArrayList<>();
    RelativeLayout rlretailer;
    LinearLayout retailerContainer;
    Context context;
    EditText searchRetailer;
    ArrayList<LinearLayout> retailerViewList=new ArrayList<>();
    int pageNo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer);
        context=RetailerActivity.this;
        retailerContainer= (LinearLayout) findViewById(R.id.retailerContainer);
        searchRetailer= (EditText) findViewById(R.id.searchRetailer);
        if (getIntent().getStringExtra("day_id")!=null)
        {
            day_id=getIntent().getStringExtra("day_id");
        }
        RetailerHelper db = new RetailerHelper(getApplicationContext());
        int count = db.numberOfRows(RetailerHelper.RETAILER_TB_NAME);
        Log.e("count of retailres",count+"");
        db.close();
        if (count == 0) {
            loadRetailer();
        } else {
            loadRetailerFormDatabase();
        }


    }
    public void loadRetailer()
    {
        pageNo++;
        new GetData(this,CallFor.RETAILERlIST,"?day_id="+day_id+"&pageNumber=" + pageNo).execute();
    }
    public void loadRetailerFormDatabase()
    {
        retailerBeans.clear();//clear previous records
        RetailerHelper itemHelper = new RetailerHelper(getApplicationContext());
        Log.e("retailer,", new Gson().toJson(itemHelper.getAllItems()));
        retailerBeans.addAll(itemHelper.getAllItems());
        setData(retailerBeans);
        searchRetailer.addTextChangedListener(searchRetailerWatcher);
        Log.e("retailer beans", new Gson().toJson(retailerBeans));
    }
    public void saveRetailerInRetailerTable(ArrayList<RetailerBean> retailerBeans)
    {
        try {
            if (retailerBeans.size() > 0) {
                RetailerHelper db = new RetailerHelper(getApplicationContext());
                db.saveRetailer(retailerBeans);
                db.close();
            }
        } catch (Exception e) {
            Log.e("Error:Saving Locations", e + "");
        }
    }
    @Override
    public void onGetResponse(String result, String callFor) {
        super.onGetResponse(result, callFor);
        Log.e("result", result);
        if (callFor.equalsIgnoreCase(CallFor.RETAILERlIST))
        {
            retailerResponse=new Gson().fromJson(result,RetailerResponse.class);
            Log.e("status", retailerResponse.getStatus() + "");
            if (retailerResponse!=null) {
                if (retailerResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                    Log.e("enter", "enter");
                    Log.e("size", retailerResponse.getListOfRetailer().size() + "");
                    saveRetailerInRetailerTable(retailerResponse.getListOfRetailer());
                    setData(retailerResponse.getListOfRetailer());
                    searchRetailer.addTextChangedListener(searchRetailerWatcher);
                    String pagesize= AppData.getPageSize(context);
                    int pageSize=Integer.parseInt(pagesize);
                    if (retailerResponse.getListOfRetailer().size() == pageSize) {
                        loadRetailer();
                        Log.e("enter item", "item");
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(rlretailer, retailerResponse.getMessage(), Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);

                    snackbar.show();
                }
            }else
            {
                Snackbar snackbar = Snackbar
                        .make(rlretailer, "Server is not responding", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);
                snackbar.show();
            }
        }
    }

    private void setData(ArrayList<RetailerBean> retailerBean) {
        retailerContainer.removeAllViews();
        for (int i=0;i<retailerBean.size();i++) {
            try {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout ll_retailer = (LinearLayout) inflater.inflate(R.layout.retailer_view, null);
                TextView retailerName,retailerArea;
                ll_retailer.setTag(retailerBean.get(i));
                retailerName= (TextView) ll_retailer.findViewById(R.id.retailerName);
                retailerArea= (TextView) ll_retailer.findViewById(R.id.retailerArea);
                retailerName.setText(retailerBean.get(i).getRetailer_name());
                retailerArea.setText(retailerBean.get(i).getRetailer_area());
                Log.e("addview", "view");
                retailerContainer.addView(ll_retailer);
                retailerViewList.add(ll_retailer);

                ll_retailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetailerBean retailerBean1= (RetailerBean) ll_retailer.getTag();
                        Log.e("retailer id",retailerBean1.getRetailer_id()+"");
                        Log.e("retaier name",retailerBean1.getRetailer_name()+"");
                        String retailerid=retailerBean1.getRetailer_id()+"";
                        Intent createOrderActivity=new Intent(RetailerActivity.this,CreateOrderActivity.class);
                        createOrderActivity.putExtra("retailerCode",retailerid);
                        startActivity(createOrderActivity);
                    }
                });
            }catch (Exception e)
            {
                Log.e("error","");
            }
        }
    }

    private final TextWatcher searchRetailerWatcher = new TextWatcher()
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchString;
            searchString=searchRetailer.getText()+"";
            Log.e("text",searchString);
                for (int j = 0; j < retailerViewList.size(); j++) {
                    LinearLayout retailerLayout=retailerViewList.get(j);
                    RetailerBean retailerBean1= (RetailerBean) retailerLayout.getTag();
                    if (retailerBean1.getRetailer_name().toUpperCase().contains(searchString.toUpperCase())) {
                        retailerLayout.setVisibility(View.VISIBLE);
                    }else
                    {
                        retailerLayout.setVisibility(View.GONE);
                    }
                }
        }
    };

    public void backpress(View view)
    {
        super.onBackPressed();
    }

    public void closeSearch(View view)
    {
        searchRetailer.setText("");
    }
}
