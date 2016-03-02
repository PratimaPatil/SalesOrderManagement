package com.gls.som.item;

import java.util.ArrayList;

/**
 * Created by pratima on 26/2/16.
 */
public class ItemResponse {
    String status;
    String message;
   ArrayList<ItemBean> listOfItem=new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ItemBean> getListOfItem() {
        return listOfItem;
    }

    public void setListOfItem(ArrayList<ItemBean> listOfItem) {
        this.listOfItem = listOfItem;
    }
}
