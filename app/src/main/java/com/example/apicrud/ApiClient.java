package com.example.apicrud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://us-central1-be-tp3-a.cloudfunctions.net/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
    public static AutoService getAutoService() {
        return RetrofitAuto.getAuto(BASE_URL).create(AutoService.class);
    }

}