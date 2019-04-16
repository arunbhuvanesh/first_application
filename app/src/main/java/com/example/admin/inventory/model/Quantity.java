package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quantity {
    @SerializedName("sum(quantity)")
    @Expose
    private String sumQuantity;

    public String getSumQuantity() {
        return sumQuantity;
    }

    public void setSumQuantity(String sumQuantity) {
        this.sumQuantity = sumQuantity;
    }

}
