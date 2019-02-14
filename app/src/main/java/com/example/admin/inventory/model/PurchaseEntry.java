package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseEntry {
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("p_amount")
    @Expose
    private String pAmount;
    @SerializedName("debit_amt")
    @Expose
    private String debitAmt;
    @SerializedName("bal_amt")
    @Expose
    private String balAmt;

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPAmount() {
        return pAmount;
    }

    public void setPAmount(String pAmount) {
        this.pAmount = pAmount;
    }

    public String getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(String debitAmt) {
        this.debitAmt = debitAmt;
    }

    public String getBalAmt() { return balAmt; }

    public void setBalAmt(String balAmt) {
        this.balAmt = balAmt;
    }
}
