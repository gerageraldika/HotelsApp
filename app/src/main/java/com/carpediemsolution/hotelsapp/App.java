package com.carpediemsolution.hotelsapp;


import android.app.Application;

import com.carpediemsolution.hotelsapp.api.WebApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Юлия on 25.07.2017.
 */

public class App extends Application {

    private static WebApi webApi;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit mRetrofit;
        //initialize retrofit client
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //invalid url
                .baseUrl("https://invalid url")
                .build();

        webApi = mRetrofit.create(WebApi.class);
    }

    public static WebApi getWebApi() {
        return webApi;
    }
}
