package com.example.admin.inventory.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //private static final String BASEURL="http://arunbhuvanesh29.000webhostapp.com/";
    private static final String BASEURL="http://192.168.0.8/inventory/";
    private static Retrofit retrofit;
    public static Retrofit getApiClient()
    {
        if(retrofit==null)
        {
            final OkHttpClient okHttpClient=new OkHttpClient.Builder().
                    connectTimeout(20, TimeUnit.SECONDS).
                    writeTimeout(20,TimeUnit.SECONDS).
                    readTimeout(20,TimeUnit.SECONDS).build();
            retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
