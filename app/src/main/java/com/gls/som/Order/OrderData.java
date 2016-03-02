package com.gls.som.Order;

import com.gls.som.item.ItemBean;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by prajyot on 1/3/16.
 */
public class OrderData {
    String retail_code;
    ArrayList<ItemBean> orderDetail;

    public String getRetail_code() {
        return retail_code;
    }

    public void setRetail_code(String retail_code) {
        this.retail_code = retail_code;
    }

    public ArrayList<ItemBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<ItemBean> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
