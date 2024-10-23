package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public Retrofit getClient(String baseUrl) {
        // 느슨한 JSON 파싱을 허용하는 Gson 인스턴스
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Gson 인스턴스를 Retrofit에 추가
                    .build();
        }
        return retrofit;
    }
}
