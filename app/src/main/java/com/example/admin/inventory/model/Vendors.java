package com.example.admin.inventory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Vendors {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("v_name")
    @Expose
    private String vName;
    @SerializedName("v_company")
    @Expose
    private String vCompany;
    @SerializedName("v_address")
    @Expose
    private String vAddress;
    @SerializedName("v_phone")
    @Expose
    private String vPhone;
    @SerializedName("v_email")
    @Expose
    private String vEmail;
    @SerializedName("status")
    @Expose
    private String status;

    public Vendors(String id, String vName, String vCompany, String vAddress, String vPhone) {
        this.id = id;
        this.vName = vName;
        this.vCompany = vCompany;
        this.vAddress = vAddress;
        this.vPhone = vPhone;
    }

    public static final Comparator<Vendors> BY_ALPHABETICAL = new Comparator<Vendors>() {
        @Override
        public int compare(Vendors o1, Vendors o2) {

            return o1.vName.compareTo(o2.vName);
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String vId) {
        this.id = id;
    }

    public String getVName() {
        return vName;
    }

    public void setVName(String vName) {
        this.vName = vName;
    }

    public String getVCompany() {
        return vCompany;
    }

    public void setVCompany(String vCompany) {
        this.vCompany = vCompany;
    }

    public String getVAddress() {
        return vAddress;
    }

    public void setVAddress(String vAddress) {
        this.vAddress = vAddress;
    }

    public String getVPhone() {
        return vPhone;
    }

    public void setVPhone(String vPhone) {
        this.vPhone = vPhone;
    }

    public String getVEmail() {
        return vEmail;
    }

    public void setVEmail(String vEmail) {
        this.vEmail = vEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
