package com.example.myapplication;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃 30초
                    .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃 30초
                    .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃 30초
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.206.102.62/")  // 서버 URL
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
