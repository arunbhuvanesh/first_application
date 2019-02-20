package com.example.admin.inventory.remote;


import com.example.admin.inventory.model.Admin;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.model.Itemlist;
import com.example.admin.inventory.model.PurchaseEntry;
import com.example.admin.inventory.model.Vendors;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    /*admin authentication*/
    @GET("login.php")
    Call<Admin> login(@Query("username")String username, @Query("password")String password);


    /*vendor implemnetation*/

    @FormUrlEncoded
    @POST("addvendors.php")
    Call<ResponseBody> addvendor(@Field("v_name") String name, @Field("v_company") String company, @Field("v_address") String address, @Field("v_phone") String phone,@Field("v_email")String mail);

    @GET("jsonencode.php")
    Call<List<Vendors>> getUsers();

    @GET("vendor_update.php")
    Call<ResponseBody> updatevendor(@Query("id") String id, @Query("v_name") String name, @Query("v_company") String company, @Query("v_address") String address, @Query("v_phone") String phone,@Query("v_email")String mail);

    @FormUrlEncoded
    @POST("vendor_delete.php")
    Call<ResponseBody> deleteVendor(@Field("id") String id);

    /*puchase entry*/

    @FormUrlEncoded
    @POST("purchase_entry.php")
    Call<ResponseBody> purchaseentry(@Field("product_name") String productname, @Field("item") String item, @Field("vendor_name") String vendorname, @Field("quantity") int quantity, @Field("date") String date, @Field("p_amt") int p_amount, @Field("d_amt") int debit_amt, @Field("bal_amt") int bal_amt);

    /*item list for spinner*/
    @GET("product_json.php")
    Call<ArrayList<Itemlist>> getItemList();

    /*adding customers implementation*/
    @FormUrlEncoded
    @POST("adduser.php")
    Call<ResponseBody> addCustomers(@Field("username")String username,@Field("address")String address,@Field("area") String area,@Field("email")String email,@Field("phone")String phone,@Field("reference")String ref,@Field("r_phone")String refphone);


    @GET("user_json.php")
    Call<List<Customers>> getCustomerEntries();

    @FormUrlEncoded
    @POST("user_delete.php")
    Call<ResponseBody> deleteCustomers(@Field("id") String id);


    @GET("user_update.php")
    Call<ResponseBody> updateCustomers(@Query("id")String id, @Query("username") String username, @Query("address") String address, @Query("area") String area, @Query("email") String email,@Query("phone")String phone,@Query("reference")String reference,@Query("r_phone") String r_phone );


    /*sales entry*/

    @FormUrlEncoded
    @POST("purchasing_user.php")
    Call<ResponseBody> salesentry(@Field("cname") String cname, @Field("item") String item, @Field("quantity") int quantity, @Field("date") String date, @Field("s_amt") int s_amount, @Field("d_amt") int debit_amt, @Field("bal_amt") int bal_amt);



}
