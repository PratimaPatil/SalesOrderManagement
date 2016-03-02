package com.gls.som.item;

/**
 * Created by pratima on 26/2/16.
 */
public class ItemBean {

    String barcode;
    int box_qty;
    int deal_active_flag;
    int deal_amount;
    int deal_on_qty;
    String deal_type;
    String image_name;
    double inner_qty;
    int isactive_item;
    int item_code;
    int item_group_code;
    int item_mrp;
    String item_name;
    double item_rate;
    String item_type;
    double tax;
    int upd_dateTime;
    int version;
    int weight;
    String weight_unit;
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return getItem_name()+" ["+getItem_rate()+"|"+getItem_mrp()+" ]";
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBox_qty() {
        return box_qty;
    }

    public void setBox_qty(int box_qty) {
        this.box_qty = box_qty;
    }

    public int getDeal_active_flag() {
        return deal_active_flag;
    }

    public void setDeal_active_flag(int deal_active_flag) {
        this.deal_active_flag = deal_active_flag;
    }

    public int getDeal_amount() {
        return deal_amount;
    }

    public void setDeal_amount(int deal_amount) {
        this.deal_amount = deal_amount;
    }

    public int getDeal_on_qty() {
        return deal_on_qty;
    }

    public void setDeal_on_qty(int deal_on_qty) {
        this.deal_on_qty = deal_on_qty;
    }

    public String getDeal_type() {
        return deal_type;
    }

    public void setDeal_type(String deal_type) {
        this.deal_type = deal_type;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public double getInner_qty() {
        return inner_qty;
    }

    public void setInner_qty(double inner_qty) {
        this.inner_qty = inner_qty;
    }

    public int getIsactive_item() {
        return isactive_item;
    }

    public void setIsactive_item(int isactive_item) {
        this.isactive_item = isactive_item;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public int getItem_group_code() {
        return item_group_code;
    }

    public void setItem_group_code(int item_group_code) {
        this.item_group_code = item_group_code;
    }

    public int getItem_mrp() {
        return item_mrp;
    }

    public void setItem_mrp(int item_mrp) {
        this.item_mrp = item_mrp;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_rate() {
        return item_rate;
    }

    public void setItem_rate(double item_rate) {
        this.item_rate = item_rate;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getUpd_dateTime() {
        return upd_dateTime;
    }

    public void setUpd_dateTime(int upd_dateTime) {
        this.upd_dateTime = upd_dateTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }
}

