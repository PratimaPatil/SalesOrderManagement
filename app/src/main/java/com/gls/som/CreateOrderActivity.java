package com.gls.som;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gls.som.Order.OrderData;
import com.gls.som.item.ItemBean;
import com.gls.som.item.ItemResponse;
import com.gls.som.database.ItemHelper;
import com.gls.som.utils.AppData;
import com.gls.som.utils.CallFor;
import com.gls.som.utils.GetData;
import com.gls.som.utils.PostData;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Timer;

public class CreateOrderActivity extends BaseActivity {
    int pageNo = 0;
    AutoCompleteTextView selectitemautocomplete;
    ItemResponse itemResponse;
    ArrayList<ItemBean> itemBeans = new ArrayList<>();
    Context context;
    TextView total_amt_of_item, txtitemrate,retailerName,grandTototal,retailerArea;
    EditText qtyofitem;
    ArrayAdapter itemAdapter;
    LinearLayout ll_item_added_layout;
    String retailer_code,retailer_name,retailer_area;
    ArrayList<ItemBean> orderedItemList = new ArrayList<>();
    RelativeLayout rl_mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = CreateOrderActivity.this;
        setContentView(R.layout.activity_create_order);
        selectitemautocomplete = (AutoCompleteTextView) findViewById(R.id.selectitemautocomplete);
        ll_item_added_layout = (LinearLayout) findViewById(R.id.ll_item_added_layout);
        rl_mainLayout= (RelativeLayout) findViewById(R.id.rl_mainLayout);
        retailerName= (TextView) findViewById(R.id.retailerName);
        grandTototal= (TextView) findViewById(R.id.grandTototal);
        retailerArea= (TextView) findViewById(R.id.retailerArea);
        ItemHelper db = new ItemHelper(getApplicationContext());
        int count = db.numberOfRows(ItemHelper.ITEMS_TB_NAME);
        db.close();
        if (getIntent().getStringExtra("retailerCode") != null) {
            retailer_code = getIntent().getStringExtra("retailerCode");
            retailer_name = getIntent().getStringExtra("retailerName");
            retailer_area = getIntent().getStringExtra("retailerArea");
            retailerName.setText(retailer_name);
            retailerArea.setText(retailer_area);
            Log.e("retaile_code", retailer_code);
        }
        itemAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_row, itemBeans);
        selectitemautocomplete.setAdapter(itemAdapter);
        if (count == 0) {
            loadItems();
        } else {
            loadItemsFormDatabase();
        }
        selectitemautocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = selectitemautocomplete.getText().toString().toLowerCase();
                Log.e("str", str);
                Log.e("orderedItemList", orderedItemList.size() + "");
                Log.e("itemBeans", itemBeans.size() + "");

                int showPopup=findItemInOrder(str);
                if (showPopup==-1)
                {
                    findItemFromList(showPopup, itemBeans,str);
                }else
                {
                    findItemFromList(showPopup, orderedItemList,str);
                }
            }
        });
    }

    @Override
    public void onGetResponse(String result, String callFor) {
        super.onGetResponse(result, callFor);

        if (callFor.equalsIgnoreCase(CallFor.SAVEORDER)) {
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                String status, message;
                status = (String) json.get("status");
                message = (String) json.get("message");
                if (json != null) {
                    if (status.equalsIgnoreCase("success")) {
                        retailerArea.setText(retailer_area);
                        retailerName.setText(retailer_name);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Intent home=new Intent(CreateOrderActivity.this,HomeActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(home);
                    }else
                    {
                        Snackbar snackbar = Snackbar
                                .make(rl_mainLayout, message, Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                        snackbar.show();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(rl_mainLayout, "Server is not responding", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("SAVE ORDER", result);
        } else if (callFor.equalsIgnoreCase(CallFor.ITEMLIST)) {
            itemResponse = new Gson().fromJson(result, ItemResponse.class);
            if (itemResponse != null) {
                Log.e("items size", itemResponse.getListOfItem().size() + "");
                itemBeans.addAll(itemResponse.getListOfItem());
                Log.e("item bean size", itemBeans.size() + "");
                saveItemsInItemTable(itemResponse);
                String pagesize= AppData.getPageSize(context);
                int pageSize=Integer.parseInt(pagesize);
                if (itemResponse.getListOfItem().size() == pageSize) {
                    loadItems();
                    Log.e("enter item", "item");
                }
            }
        }
    }

    public int findItemInOrder(String searchString) {
        int index = -1;
        for (int i = 0; i < orderedItemList.size(); i++) {
            if (orderedItemList.get(i).toString().equalsIgnoreCase(searchString)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public BigDecimal calculateGrandTotal(ArrayList<ItemBean> orderedListItems)
    {
       BigDecimal grandTotal=new BigDecimal("0.0");
        for (int i=0;i<orderedListItems.size();i++)
        {
            BigDecimal rate=new BigDecimal(orderedListItems.get(i).getItem_rate());
            BigDecimal quantity=new BigDecimal(orderedListItems.get(i).getQuantity());
            grandTotal=grandTotal.add((rate.multiply(quantity)));
        }
        return  grandTotal;
    }
    private void findItemFromList(final int indexInOrder, ArrayList<ItemBean> itemBeans,String compareString) {

        //final int indexInOrder = findItemInOrder(compareString);
        if (indexInOrder >= 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateOrderActivity.this);
            alert.setMessage("Item is allready added, do you want to edit item?");
            alert.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.e("item", new Gson().toJson(orderedItemList.get(indexInOrder)));
                    boolean isEdit = true;
                    showItemDetails(orderedItemList.get(indexInOrder), isEdit);
                    dialogInterface.dismiss();
                }
            }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                }
            });
            alert.show();
        } else {
            Log.e("Item","Item");
            for (int i = 0; i < itemBeans.size(); i++) {
                if (itemBeans.get(i).toString().toLowerCase().equalsIgnoreCase(compareString)) {
                    Log.e("item name", itemBeans.get(i).toString());
                    showItemDetails(itemBeans.get(i), false);

                }
            }
        }
    }

    public void showItemDetails(final ItemBean itemBean, final boolean isEdit) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_detail_layout);
        dialog.setTitle(itemBean.getItem_name());
        TextView txtmrp = (TextView) dialog.findViewById(R.id.txtmrp);
        TextView txtboxqty = (TextView) dialog.findViewById(R.id.txtboxqty);
        TextView txtitemtype = (TextView) dialog.findViewById(R.id.txtitemtype);
        txtitemrate = (TextView) dialog.findViewById(R.id.txtitemrate);
        qtyofitem = (EditText) dialog.findViewById(R.id.qtyofitem);
        total_amt_of_item = (TextView) dialog.findViewById(R.id.total_amt_of_item);
        qtyofitem.addTextChangedListener(calculateTotalTextWatcher);
        TextView txtpromoline = (TextView) dialog.findViewById(R.id.txtpromoline);
        txtmrp.setText(itemBean.getItem_mrp() + "");
        txtitemrate.setText(itemBean.getItem_rate() + "");
        txtboxqty.setText(itemBean.getBox_qty() + "");
        txtitemtype.setText(itemBean.getItem_type() + "");
        Log.e("item name", itemBean.getDeal_type() + "");
        if (itemBean.getDeal_type().equalsIgnoreCase("item")) {
            txtpromoline.setText(itemBean.getDeal_amount() + " item free on " + itemBean.getDeal_on_qty() + " items");
        } else {
            txtpromoline.setText(itemBean.getDeal_amount() + " off on " + itemBean.getDeal_on_qty() + " items");
        }
        qtyofitem.setText(itemBean.getQuantity() + "");
        Button okButton = (Button) dialog.findViewById(R.id.btnok);
        Button cancelButton = (Button) dialog.findViewById(R.id.btncancel);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemQuntity = qtyofitem.getText().toString();


                if (!itemQuntity.equalsIgnoreCase("")) {
                    int itemQuantity = Integer.parseInt(itemQuntity);
                    if (itemQuantity != 0) {
                        Log.e("quantity tobe set", itemQuntity + "");
                        itemBean.setQuantity(itemQuantity);
                        if (isEdit) {
                            String findString = itemBean.toString();
                            replaceQuantity(findString);
                            orderedItemList.add(itemBean);
                            showAddedItemToOrderList(orderedItemList);
                        } else {
                            orderedItemList.add(itemBean);
                            showAddedItemToOrderList(orderedItemList);
                        }

                        Log.e("size of orderd list", orderedItemList.size() + "");
                        Log.e("quantity", itemBean.getQuantity() + "");

                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectitemautocomplete.setTag("");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void replaceQuantity(String findStr) {
        Log.e("findStr",findStr);
        for (int i = 0; i < orderedItemList.size(); i++) {
            Log.e("str",orderedItemList.get(i).toString());
            if (orderedItemList.get(i).toString().equalsIgnoreCase(findStr)) {
                orderedItemList.remove(i);
                Log.e("item removed", "remove");
            }
        }
    }


    private void showAddedItemToOrderList(final ArrayList<ItemBean> orderedItemList) {
        selectitemautocomplete.setText("");
        ll_item_added_layout.removeAllViews();
        for (int i = 0; i < orderedItemList.size(); i++) {
            View to_add_item = getLayoutInflater().inflate(R.layout.oreder_item_row, null);
            to_add_item.setTag(orderedItemList.get(i));
            TextView txt_item_name = (TextView) to_add_item.findViewById(R.id.txt_item_name);
            TextView txt_item_mrp = (TextView) to_add_item.findViewById(R.id.txt_item_mrp);
            TextView txt_item_rate = (TextView) to_add_item.findViewById(R.id.txt_item_rate);
            TextView txt_item_total = (TextView) to_add_item.findViewById(R.id.txt_item_total);
            TextView txt_item_qty = (TextView) to_add_item.findViewById(R.id.txt_item_qty);
            LinearLayout deleteItem= (LinearLayout) to_add_item.findViewById(R.id.deleteItem);
            txt_item_qty.setTag(orderedItemList.get(i));
            deleteItem.setTag(orderedItemList.get(i));
            txt_item_name.setText(orderedItemList.get(i).getItem_name());
            txt_item_mrp.setText(orderedItemList.get(i).getItem_mrp() + "");
            txt_item_rate.setText(orderedItemList.get(i).getItem_rate() + "");
            txt_item_qty.setText(orderedItemList.get(i).getQuantity()+"");
            txt_item_total.setText(calculateTotal(orderedItemList.get(i).getQuantity(),orderedItemList.get(i).getItem_rate()) + "");
            Log.e("qunt to set", orderedItemList.get(i).getQuantity() + "");
            ll_item_added_layout.addView(to_add_item);

            txt_item_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemBean clickItem = (ItemBean) v.getTag();
                    Log.e("selected item", new Gson().toJson(clickItem));
                    showItemDetails(clickItem, true);

                }
            });
            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CreateOrderActivity.this);
                    alert.setMessage("Are you sure,you want to delete the item?");
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            ItemBean item= (ItemBean) v.getTag();
                            String  toDelete=item.toString();
                            Log.e("enter into dlete",toDelete);
                            replaceQuantity(toDelete);
                            showAddedItemToOrderList(orderedItemList);

                        }
                    });
                    alert.show();
                }
            });
        }
        grandTototal.setText(round(calculateGrandTotal(orderedItemList)+""));
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
            int qty = 0;
            double rate=0.0;
            final String str = editable.toString().trim();
            rate=Double.parseDouble(txtitemrate.getText().toString());
            if (!str.equalsIgnoreCase("")) {
                qty = Integer.parseInt(str);
            }
            if (qty > 0) {
                total_amt_of_item.setText(calculateTotal(qty,rate) + "");
            } else if (qty == 0) {
                total_amt_of_item.setText(calculateTotal(qty,rate) + "");
            }
        }
    };

    private BigDecimal calculateTotal(int quantity,double rate) {
        BigDecimal totalOfItem = new BigDecimal("0.0");
        String rate1 = "";
        rate1 = rate + "";
        BigDecimal qtyOfItem = new BigDecimal(quantity);
        BigDecimal rateOfItem = new BigDecimal(rate1);
        totalOfItem = qtyOfItem.multiply(rateOfItem);
        return totalOfItem;
    }

    private void loadItems() {

        pageNo++;
        new GetData(this, CallFor.ITEMLIST, "?pageNumber=" + pageNo).execute();
    }

    public void loadItemsFormDatabase() {
        Log.e("load from database", "db");
        itemBeans.clear();//clear previous records
        ItemHelper itemHelper = new ItemHelper(getApplicationContext());
        Log.e("items,", new Gson().toJson(itemHelper.getAllItems()));
        itemBeans.addAll(itemHelper.getAllItems());
        itemAdapter.notifyDataSetChanged();
        Log.e("item beans", new Gson().toJson(itemBeans));
        Log.e("size from database", itemBeans.size() + "");
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

    public void backpress(View view) {
        super.onBackPressed();
    }

    public void closeSearch(View view) {
        selectitemautocomplete.setText("");
    }

    public void saveOrder(View view) {
        if (orderedItemList.size()!=0) {
            OrderData orderBean = new OrderData();
            orderBean.setRetail_code(retailer_code);
            orderBean.setOrderDetail(orderedItemList);

            new PostData(new Gson().toJson(orderBean), this, CallFor.SAVEORDER).execute();
        }else
        {
            Snackbar snackbar = Snackbar
                    .make(rl_mainLayout, "Please add items to order", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();
        }
    }

    public static String round(String amount)
    {
        String str= amount;
        try{
            double amt=Double.parseDouble(amount);
            int rounded = (int)amt;
            if(amt == rounded){
                str = rounded+"";
                Log.e("str1",str);
            } else {
                str = String.format("%.2f",amt);
                Log.e("str2",str);
            }
        }
        catch (Exception e){
            str = amount;
            Log.e("str3",e+"");
        }
        return  str;
    }

}
