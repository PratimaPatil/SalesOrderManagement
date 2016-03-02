package com.gls.som.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.gls.som.login.LoginResponse;
import com.google.gson.Gson;


public class AppData {
    SharedPreferences sp;
    public AppData(Activity activity){
       sp = activity.getSharedPreferences("appData",0);
    }

    public static LoginResponse getLoginResponse(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
        String loginresponse= sp.getString(Constants.LOGIN_RESP, "");
        if (loginresponse.equals(""))
        {
            return  null;
        }else
        {
            return new Gson().fromJson(loginresponse,LoginResponse.class);
        }
    }
    public static void setLoginResponse(Context context,LoginResponse loginResponse){
        SharedPreferences sp = context.getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.LOGIN_RESP,new Gson().toJson(loginResponse));
        editor.commit();
    }

    public static void setPageSize(Context context,String pageNo)
    {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.PAGE_NO,pageNo);
        editor.commit();
    }

    public static String getPageSize(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
        String pageno= sp.getString(Constants.PAGE_NO, "");
        if (pageno.equalsIgnoreCase(""))
        {
            return  null;
        }else
        {
           return pageno;
        }
    }


}
