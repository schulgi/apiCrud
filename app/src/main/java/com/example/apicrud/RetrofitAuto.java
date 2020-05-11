package com.example.apicrud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAuto {

        private static Retrofit retrofit = null;

        public static Retrofit getAuto(String url){
            if(retrofit.equals(null))
            {retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            }
            return retrofit;
        }
}
