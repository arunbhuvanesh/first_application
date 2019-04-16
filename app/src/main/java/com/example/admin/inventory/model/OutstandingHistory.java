package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OutstandingHistory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("deposit_date")
    @Expose
    private String depositDate;
    @SerializedName("d_amt")
    @Expose
    private String dAmt;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }

    public String getDAmt() {
        return dAmt;
    }

    public void setDAmt(String dAmt) {
        this.dAmt = dAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
