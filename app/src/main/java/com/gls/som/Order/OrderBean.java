package com.gls.som.Order;

import java.util.ArrayList;

/**
 * Created by pratima on 1/3/16.
 */
public class OrderBean {
     int order_no ;
     int distb_code ;
     int retail_code ;
     String creted_user ;
     String order_date ;
     String po_date ;
     String invoice_date ;
     String delivery_date ;
     int total_item ;
     double price_total_value ;
     double mrp_total_value ;
     double total_discount ;
     double total_discount_item ;
     String suggestion_remark ;
     String status_code ;
     String print_flag ;
     int tax_no ;
     double tax_percentage ;
     double total_price_amount ;
     double additional_discount ;
     double balance ;
     String order_remark ;
     ArrayList<OrderDetailBean> orderDetail ;

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getDistb_code() {
        return distb_code;
    }

    public void setDistb_code(int distb_code) {
        this.distb_code = distb_code;
    }

    public int getRetail_code() {
        return retail_code;
    }

    public void setRetail_code(int retail_code) {
        this.retail_code = retail_code;
    }

    public String getCreted_user() {
        return creted_user;
    }

    public void setCreted_user(String creted_user) {
        this.creted_user = creted_user;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPo_date() {
        return po_date;
    }

    public void setPo_date(String po_date) {
        this.po_date = po_date;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }

    public double getPrice_total_value() {
        return price_total_value;
    }

    public void setPrice_total_value(double price_total_value) {
        this.price_total_value = price_total_value;
    }

    public double getMrp_total_value() {
        return mrp_total_value;
    }

    public void setMrp_total_value(double mrp_total_value) {
        this.mrp_total_value = mrp_total_value;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public double getTotal_discount_item() {
        return total_discount_item;
    }

    public void setTotal_discount_item(double total_discount_item) {
        this.total_discount_item = total_discount_item;
    }

    public String getSuggestion_remark() {
        return suggestion_remark;
    }

    public void setSuggestion_remark(String suggestion_remark) {
        this.suggestion_remark = suggestion_remark;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getPrint_flag() {
        return print_flag;
    }

    public void setPrint_flag(String print_flag) {
        this.print_flag = print_flag;
    }

    public int getTax_no() {
        return tax_no;
    }

    public void setTax_no(int tax_no) {
        this.tax_no = tax_no;
    }

    public double getTax_percentage() {
        return tax_percentage;
    }

    public void setTax_percentage(double tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    public double getTotal_price_amount() {
        return total_price_amount;
    }

    public void setTotal_price_amount(double total_price_amount) {
        this.total_price_amount = total_price_amount;
    }

    public double getAdditional_discount() {
        return additional_discount;
    }

    public void setAdditional_discount(double additional_discount) {
        this.additional_discount = additional_discount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOrder_remark() {
        return order_remark;
    }

    public void setOrder_remark(String order_remark) {
        this.order_remark = order_remark;
    }

    public ArrayList<OrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
