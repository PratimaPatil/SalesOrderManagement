package com.gls.som.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gls.som.item.ItemBean;

import java.util.ArrayList;

/**
 * Created by pratima on 29/2/16.
 */
public class ItemHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "items.db";
    public static final String ITEMS_TB_NAME = "items";
    public static final String BARCODE = "barcode";
    public static final String BOX_QTY = "BOX_QTY";
    public static final String DEAL_ACTIVE_FLAG = "DEAL_ACTIVE_FLAG";
    public static final String DEAL_AMOUNT = "DEAL_AMOUNT";
    public static final String DEAL_ON_QTY = "DEAL_ON_QTY";
    public static final String DEAL_TYPE = "deal_type";
    public static final String IMAGE_NAME = "image_name";
    public static final String INNER_QTY = "INNER_QTY";
    public static final String ISACTIVE_ITEM = "ISACTIVE_ITEM";
    public static final String ITEM_CODE = "ITEM_CODE";
    public static final String ITEM_GROUP_CODE = "ITEM_GROUP_CODE";
    public static final String ITEM_MRP = "ITEM_MRP";
    public static final String ITEM_RATE = "ITEM_RATE";
    public static final String TAX = "TAX";
    public static final String UPD_DATETIME = "UPD_DATETIME";
    public static final String VERSION = "VERSION";
    public static final String WEIGHT = "WEIGHT";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_TYPE = "item_type";
    public static final String WEIGHT_UNIT = "weight_unit";

    public ItemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + ITEMS_TB_NAME + "'");
        db.execSQL("create table " + ITEMS_TB_NAME + "(" + ITEM_CODE + " integer primary key, " + BARCODE + " text, " + BOX_QTY + " text, "
                + DEAL_ACTIVE_FLAG + " text, " + DEAL_AMOUNT + " text," + DEAL_ON_QTY + " text," + DEAL_TYPE + " text," + IMAGE_NAME + " text,"
                + INNER_QTY + " text," + ISACTIVE_ITEM + " text," + ITEM_GROUP_CODE + " text," + ITEM_MRP + " text," + ITEM_RATE + " text,"
                + TAX + " text," + UPD_DATETIME + " text," + VERSION + " text," + WEIGHT + " text," + ITEM_NAME + " text," + ITEM_TYPE + " text,"
                + WEIGHT_UNIT + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean saveItems  (ArrayList<ItemBean> itemBeans) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<itemBeans.size();i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BARCODE, itemBeans.get(i).getBarcode());
            contentValues.put(BOX_QTY, itemBeans.get(i).getBox_qty());
            contentValues.put(DEAL_ACTIVE_FLAG, itemBeans.get(i).getDeal_active_flag());
            contentValues.put(DEAL_AMOUNT, itemBeans.get(i).getDeal_amount());
            contentValues.put(DEAL_ON_QTY, itemBeans.get(i).getDeal_on_qty());
            contentValues.put(DEAL_TYPE, itemBeans.get(i).getDeal_type());
            contentValues.put(IMAGE_NAME, itemBeans.get(i).getImage_name());
            contentValues.put(INNER_QTY, itemBeans.get(i).getInner_qty());
            contentValues.put(ISACTIVE_ITEM, itemBeans.get(i).getIsactive_item());
            contentValues.put(ITEM_CODE, itemBeans.get(i).getItem_code());
            contentValues.put(ITEM_GROUP_CODE, itemBeans.get(i).getItem_group_code());
            contentValues.put(ITEM_MRP, itemBeans.get(i).getItem_mrp());
            contentValues.put(ITEM_RATE, itemBeans.get(i).getItem_rate());
            contentValues.put(TAX, itemBeans.get(i).getTax());
            contentValues.put(UPD_DATETIME, itemBeans.get(i).getUpd_dateTime());
            contentValues.put(VERSION, itemBeans.get(i).getVersion());
            contentValues.put(WEIGHT, itemBeans.get(i).getWeight());
            contentValues.put(ITEM_NAME, itemBeans.get(i).getItem_name());
            contentValues.put(ITEM_TYPE, itemBeans.get(i).getItem_type());
            contentValues.put(WEIGHT_UNIT, itemBeans.get(i).getWeight_unit());
            db.insert(ITEMS_TB_NAME, null, contentValues);
            Log.e("Inserting", itemBeans.get(i).getItem_name() + "=>" + itemBeans.get(i).getItem_rate()+ "=>"  + itemBeans.get(i).getWeight() + "=>" );
        }
        db.close();
        return true;
    }

    public ArrayList<ItemBean> getAllItems(){
        ArrayList<ItemBean> itemBeans = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ITEMS_TB_NAME,null);
        if(cursor.moveToFirst()) {
            do {
                ItemBean bean = new ItemBean();
                bean.setItem_code(cursor.getInt(0));
                bean.setBarcode(cursor.getString(1));
                bean.setBox_qty(cursor.getInt(2));
                bean.setDeal_on_qty(cursor.getInt(3));
                bean.setDeal_amount(cursor.getInt(4));
                bean.setDeal_on_qty(cursor.getInt(5));
                bean.setDeal_type(cursor.getString(6));
                bean.setImage_name(cursor.getString(7));
                bean.setInner_qty(cursor.getDouble(8));
                bean.setIsactive_item(cursor.getInt(9));
                bean.setItem_group_code(cursor.getInt(10));
                bean.setItem_mrp(cursor.getInt(11));
                bean.setItem_rate(cursor.getDouble(12));
                bean.setTax(cursor.getDouble(13));
                bean.setUpd_dateTime(cursor.getInt(14));
                bean.setVersion(cursor.getInt(15));
                bean.setWeight_unit(cursor.getString(16));
                bean.setItem_name(cursor.getString(17));
                bean.setItem_type(cursor.getString(18));
                bean.setWeight_unit(cursor.getString(19));
                itemBeans.add(bean);
            } while (cursor.moveToNext());
        }
        db.close();
        return itemBeans;
    }

    public void clearTable(String tb_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery( "delete from "+tb_name, null );
    }

    public int numberOfRows(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_name);
        return numRows;
    }

}
