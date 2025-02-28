package com.example.myapplication.api;

import com.example.myapplication.api.WebServiceApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.0.2:8080/";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static WebServiceApi getWebServiceApi() {
        return getInstance().create(WebServiceApi.class);
    }
    public static String getBase_Url(){
        return BASE_URL;
    }
}
