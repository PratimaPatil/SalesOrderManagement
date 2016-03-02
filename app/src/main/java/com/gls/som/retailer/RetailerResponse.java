package com.gls.som.retailer;

import java.util.ArrayList;

/**
 * Created by pratima on 26/2/16.
 */
public class RetailerResponse {
    String message;
    String status;
    ArrayList<RetailerBean> listOfRetailer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<RetailerBean> getListOfRetailer() {
        return listOfRetailer;
    }

    public void setListOfRetailer(ArrayList<RetailerBean> listOfRetailer) {
        this.listOfRetailer = listOfRetailer;
    }
}
