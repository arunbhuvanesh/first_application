package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseEntry {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("v_id")
    @Expose
    private String vId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("p_amt")
    @Expose
    private String pAmt;
    @SerializedName("d_amt")
    @Expose
    private String dAmt;
    @SerializedName("b_amt")
    @Expose
    private String bAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getVId() {
        return vId;
    }

    public void setVId(String vId) {
        this.vId = vId;
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

    public String getPAmt() {
        return pAmt;
    }

    public void setPAmt(String pAmt) {
        this.pAmt = pAmt;
    }

    public String getDAmt() {
        return dAmt;
    }

    public void setDAmt(String dAmt) {
        this.dAmt = dAmt;
    }

    public String getBAmt() {
        return bAmt;
    }

    public void setBAmt(String bAmt) {
        this.bAmt = bAmt;
    }
}
