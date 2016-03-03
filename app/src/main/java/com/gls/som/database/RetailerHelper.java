package com.gls.som.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gls.som.retailer.RetailerBean;
import com.gls.som.utils.Constants;

import java.util.ArrayList;

/**
 * Created by pratima on 3/3/16.
 */
public class RetailerHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = Constants.DB_VERSION;
    public static final String DATABASE_NAME = "retailers.db";
    public static final String RETAILER_TB_NAME = "retailers";
    public static final String RETAILER_AREA = "retailer_area";
    public static final String RETAILER_ID = "retailer_id";
    public static final String RETAILER_NAME = "retailer_name";
    public RetailerHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + RETAILER_TB_NAME + "'");
        db.execSQL("create table " + RETAILER_TB_NAME + "(" + RETAILER_ID + " integer primary key, " + RETAILER_NAME + " text, " + RETAILER_AREA + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public boolean saveRetailer  (ArrayList<RetailerBean> retailerBeans) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<retailerBeans.size();i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RETAILER_ID, retailerBeans.get(i).getRetailer_id());
            contentValues.put(RETAILER_NAME, retailerBeans.get(i).getRetailer_name());
            contentValues.put(RETAILER_AREA, retailerBeans.get(i).getRetailer_area());
            db.insert(RETAILER_TB_NAME, null, contentValues);
            Log.e("Inserting", retailerBeans.get(i).getRetailer_id() + "=>" + retailerBeans.get(i).getRetailer_name() + "=>" + retailerBeans.get(i).getRetailer_area() + "=>");
        }
        db.close();
        return true;
    }
    public ArrayList<RetailerBean> getAllItems(){
        ArrayList<RetailerBean> itemBeans = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+RETAILER_TB_NAME,null);
        if(cursor.moveToFirst()) {
            do {
                RetailerBean bean = new RetailerBean();
                bean.setRetailer_id(cursor.getInt(0));
                bean.setRetailer_name(cursor.getString(1));
                bean.setRetailer_area(cursor.getString(2));

                itemBeans.add(bean);
            } while (cursor.moveToNext());
        }
        db.close();
        return itemBeans;
    }
    public void clearTable(String tb_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("delete from " + tb_name, null);
    }

    public int numberOfRows(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_name);
        return numRows;
    }
}
