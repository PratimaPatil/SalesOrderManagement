package com.gls.som.Order;

/**
 * Created by pratima on 1/3/16.
 */
public class OrderDetailBean {
     int order_no ;
     int item_code ;
     double item_rate ;
     double item_mrp ;
     int quantity ;
     double total_price_value ;
     double total_discount ;
     int total_discount_item ;
     double total_mrp_value ;
     String remark ;
     int price_version ;
     int tax_no ;
     double tax_percentage ;
     double total_price_amount ;

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public double getItem_rate() {
        return item_rate;
    }

    public void setItem_rate(double item_rate) {
        this.item_rate = item_rate;
    }

    public double getItem_mrp() {
        return item_mrp;
    }

    public void setItem_mrp(double item_mrp) {
        this.item_mrp = item_mrp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal_price_value() {
        return total_price_value;
    }

    public void setTotal_price_value(double total_price_value) {
        this.total_price_value = total_price_value;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public int getTotal_discount_item() {
        return total_discount_item;
    }

    public void setTotal_discount_item(int total_discount_item) {
        this.total_discount_item = total_discount_item;
    }

    public double getTotal_mrp_value() {
        return total_mrp_value;
    }

    public void setTotal_mrp_value(double total_mrp_value) {
        this.total_mrp_value = total_mrp_value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPrice_version() {
        return price_version;
    }

    public void setPrice_version(int price_version) {
        this.price_version = price_version;
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
}
