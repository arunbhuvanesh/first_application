package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesEntry {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("c_id")
    @Expose
    private String c_id;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("s_amt")
    @Expose
    private String sAmt;
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

    public String getCid() {
        return c_id;
    }

    public void setCid(String cid) {
        this.c_id = cid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getSAmt() {
        return sAmt;
    }

    public void setSAmt(String sAmt) {
        this.sAmt = sAmt;
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
