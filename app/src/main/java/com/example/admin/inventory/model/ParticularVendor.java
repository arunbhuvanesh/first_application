package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class ParticularVendor {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("v_name")
        @Expose
        private String vName;
        @SerializedName("item_name")
        @Expose
        private String itemName;
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

        public String getVName() {
            return vName;
        }

        public void setVName(String vName) {
            this.vName = vName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
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
