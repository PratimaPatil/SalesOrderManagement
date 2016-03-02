package com.gls.som;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gls.som.Order.OrderBean;
import com.gls.som.Order.OrderData;
import com.gls.som.Order.OrderDetailBean;
import com.gls.som.item.ItemBean;
import com.gls.som.item.ItemResponse;
import com.gls.som.database.ItemHelper;
import com.gls.som.retailer.RetailerResponse;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;
import com.gls.som.utils.PostData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

public class CreateOrderActivity extends BaseActivity {
    int pageNo=0;
    AutoCompleteTextView selectitemautocomplete;
    ItemResponse itemResponse;
    ArrayList<ItemBean> itemBeans=new ArrayList<>();
    Context context;
    TextView total_amt_of_item,txtitemrate;
    EditText qtyofitem;
    ArrayAdapter itemAdapter;
    LinearLayout ll_item_added_layout;
    String retailer_code;
    ArrayList<ItemBean> orderedItemList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=CreateOrderActivity.this;
        setContentView(R.layout.activity_create_order);
        selectitemautocomplete= (AutoCompleteTextView) findViewById(R.id.selectitemautocomplete);
        ll_item_added_layout= (LinearLayout) findViewById(R.id.ll_item_added_layout);
        ItemHelper db = new ItemHelper(getApplicationContext());
        int count = db.numberOfRows(ItemHelper.ITEMS_TB_NAME);
        db.close();
        if (getIntent().getStringExtra("retailerCode")!=null)
        {
            retailer_code=getIntent().getStringExtra("retailerCode");
            Log.e("retaile_code",retailer_code);
        }
        itemAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.item_row,itemBeans);
        selectitemautocomplete.setAdapter(itemAdapter);
        if (count == 0) {
           loadItems();
        } else {
            loadItemsFormDatabase();
        }
            selectitemautocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str=selectitemautocomplete.getText().toString().toLowerCase();
                    Log.e("str",str);
                    Log.e("orderedItemList",orderedItemList.size()+"");
                    Log.e("itemBeans",itemBeans.size()+"");
                    if (orderedItemList.size()!=0)
                    {
                        findItemFromList(str, orderedItemList);
                        Log.e("search in ordered list","");
                    }else {
                        Log.e("search in item bean","");
                         findItemFromList(str, itemBeans);
                    }
                }
            });


    }
    @Override
    public void onGetResponse(String result, String callFor) {
        super.onGetResponse(result, callFor);

        if (callFor.equalsIgnoreCase(CallFor.SAVEORDER))
        {
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                String status,message;
                status= (String) json.get("status");
                if (json!=null) {
                    if (status.equalsIgnoreCase("success")) {
                        message = (String) json.get("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(),"Server is not responding",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("SAVE ORDER", result);
        }else if (callFor.equalsIgnoreCase(CallFor.ITEMLIST))
        {
            itemResponse=new Gson().fromJson(result,ItemResponse.class);
            if (itemResponse!=null)
            {
                Log.e("items size",itemResponse.getListOfItem().size()+"");
                itemBeans.addAll(itemResponse.getListOfItem());
                Log.e("item bean size", itemBeans.size() + "");
                saveItemsInItemTable(itemResponse);
                if (itemResponse.getListOfItem().size()==50)
                {
                    loadItems();
                    Log.e("enter item","item");
                }
            }
        }

    }
    private void  findItemFromList(String compareString,ArrayList<ItemBean> itemBeans) {
        for (int i=0;i<itemBeans.size();i++)
        {
            if (itemBeans.get(i).toString().toLowerCase().equalsIgnoreCase(compareString))
            {
                 Log.e("item name",itemBeans.get(i).toString());
                    showItemDetails(itemBeans.get(i));
            }else
            {
//                AlertDialog.Builder alert = new AlertDialog.Builder(CreateOrderActivity.this);
//                alert.setMessage("Are you sure exit from application?");
//                alert.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Log.e("exit", "app EXIT");
//                    }
//                });
//                alert.show();
            }
        }
    }

    public void showItemDetails(final ItemBean itemBean)
    {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.item_detail_layout);
            dialog.setTitle(itemBean.getItem_name());
            TextView txtmrp = (TextView) dialog.findViewById(R.id.txtmrp);
            TextView txtboxqty= (TextView) dialog.findViewById(R.id.txtboxqty);
            TextView txtitemtype= (TextView) dialog.findViewById(R.id.txtitemtype);
            txtitemrate = (TextView) dialog.findViewById(R.id.txtitemrate);
            qtyofitem= (EditText) dialog.findViewById(R.id.qtyofitem);
            total_amt_of_item= (TextView) dialog.findViewById(R.id.total_amt_of_item);
            qtyofitem.addTextChangedListener(calculateTotalTextWatcher);
            TextView txtpromoline= (TextView) dialog.findViewById(R.id.txtpromoline);
            txtmrp.setText(itemBean.getItem_mrp() + "");
            txtitemrate.setText(itemBean.getItem_rate()+"");
            txtboxqty.setText(itemBean.getBox_qty() + "");
            txtitemtype.setText(itemBean.getItem_type() + "");
            Log.e("item name",itemBean.getDeal_type()+"");
            if (itemBean.getDeal_type().equalsIgnoreCase("item"))
            {
                txtpromoline.setText(itemBean.getDeal_amount() +" item free on "+itemBean.getDeal_on_qty()+" items");
            }else
            {
                txtpromoline.setText(itemBean.getDeal_amount() +" off on "+itemBean.getDeal_on_qty()+" items");
            }
            qtyofitem.setText(itemBean.getQuantity()+"");
            Button okButton = (Button) dialog.findViewById(R.id.btnok);
            Button cancelButton = (Button) dialog.findViewById(R.id.btncancel);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String itemQuntity=qtyofitem.getText().toString();
                    if (!itemQuntity.equalsIgnoreCase("")) {
                        int itemQuantity = Integer.parseInt(itemQuntity);
                        Log.e("quantity tobe set", itemQuntity + "");
                        itemBean.setQuantity(itemQuantity);
                        orderedItemList.add(itemBean);
                        addItemToOrderList(orderedItemList);
                        Log.e("size of orderd list", orderedItemList.size() + "");
                        Log.e("quantity",itemBean.getQuantity()+"");
                        dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"please enter quantity",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
    }



    private void addItemToOrderList(ArrayList<ItemBean> orderedItemList) {
        selectitemautocomplete.setText("");
        ll_item_added_layout.removeAllViews();
        for (int i=0;i<orderedItemList.size();i++) {
            View to_add_item = getLayoutInflater().inflate(R.layout.added_item_layout, null);
            to_add_item.setTag(orderedItemList.get(i));
            TextView txt_item_name = (TextView) to_add_item.findViewById(R.id.txt_item_name);
            TextView txt_item_mrp = (TextView) to_add_item.findViewById(R.id.txt_item_mrp);
            TextView txt_item_rate = (TextView) to_add_item.findViewById(R.id.txt_item_rate);
            TextView txt_item_total = (TextView) to_add_item.findViewById(R.id.txt_item_total);
            txt_item_name.setText(orderedItemList.get(i).getItem_name());
            txt_item_mrp.setText(orderedItemList.get(i).getItem_mrp() + "");
            txt_item_rate.setText(orderedItemList.get(i).getItem_rate() + "");
            txt_item_total.setText(calculateTotal(orderedItemList.get(i).getQuantity()) + "");
            Log.e("qunt to set",orderedItemList.get(i).getQuantity()+"");
            ll_item_added_layout.addView(to_add_item);

            to_add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemBean clickItem= (ItemBean) v.getTag();
                    Log.e("selected item",new Gson().toJson(clickItem));
                    showItemDetails(clickItem);

                }
            });
        }
    }
    TextWatcher calculateTotalTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        private Timer timer = new Timer();
        private final long DELAY = 000;

        @Override
        public void afterTextChanged(final Editable editable) {
            int qty=0;
            final String str = editable.toString().trim();
            if (!str.equalsIgnoreCase("")) {
                qty = Integer.parseInt(str);
            }
                if (qty > 0) {
                    total_amt_of_item.setText(calculateTotal(qty)+"");
                }else if (qty==0)
                {
                    total_amt_of_item.setText(calculateTotal(qty) + "");
                }
        }
    };

    private BigDecimal calculateTotal(int quantity) {
        BigDecimal qtyOfItem= new BigDecimal("0.0");
        BigDecimal rateOfItem= new BigDecimal("0.0");
        BigDecimal totalOfItem= new BigDecimal("0.0");
        String rate="";
        rate=txtitemrate.getText()+"";
        qtyOfItem = new BigDecimal(quantity);
        rateOfItem = new BigDecimal(rate);
        totalOfItem=qtyOfItem.multiply(rateOfItem);
        return totalOfItem;
    }

    private void loadItems() {

        pageNo++;
        new GetData(this, CallFor.ITEMLIST, "?pageNumber=" + pageNo).execute();
    }

    public void loadItemsFormDatabase(){
        Log.e("load from database","db");
        itemBeans.clear();//clear previous records
        ItemHelper itemHelper = new ItemHelper(getApplicationContext());
        Log.e("items,",new Gson().toJson(itemHelper.getAllItems()));
        itemBeans.addAll(itemHelper.getAllItems());
        itemAdapter.notifyDataSetChanged();
        Log.e("item beans", new Gson().toJson(itemBeans));
        Log.e("size from database",itemBeans.size()+"");
    }

    public void saveItemsInItemTable(ItemResponse result) {
        try {
            if (result.getListOfItem().size() > 0) {
                ItemHelper db = new ItemHelper(getApplicationContext());
                db.saveItems(result.getListOfItem());
                db.close();
            }
        } catch (Exception e) {
            Log.e("Error:Saving Locations", e + "");
        }
    }

    public void backpress(View view)
    {
        super.onBackPressed();
    }

    public void closeSearch(View view)
    {
        selectitemautocomplete.setText("");
    }
    public void saveOrder(View view)
    {
        OrderData orderBean=new OrderData();
        orderBean.setRetail_code(retailer_code);
        orderBean.setOrderDetail(orderedItemList);
        new PostData(new Gson().toJson(orderBean),this,CallFor.SAVEORDER).execute();
    }


}
