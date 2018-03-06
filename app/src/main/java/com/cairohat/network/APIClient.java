package com.cairohat.network;


import com.cairohat.constant.ConstantValues;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * APIClient handles all the Network API Requests using Retrofit Library
 **/

public class APIClient {
    
    
    // Base URL for API Requests
    private static final String BASE_URL = ConstantValues.ECOMMERCE_URL;
    private static final String Login_URL = ConstantValues.LOGIN_URL;
    
    private static APIRequests apiRequests;
    private  static APIRequests apiRequests1;
    
    
    // Singleton Instance of APIRequests
    public static APIRequests getInstance() {
        if (apiRequests == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            
            
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            
            
            apiRequests = retrofit.create(APIRequests.class);
            
            return apiRequests;
        }
        else {
            return apiRequests;
        }
    }
    public static APIRequests getInstancelogin() {
        if (apiRequests1 == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Login_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            apiRequests1 = retrofit.create(APIRequests.class);

            return apiRequests1;
        }
        else {
            return apiRequests1;
        }
    }


}


