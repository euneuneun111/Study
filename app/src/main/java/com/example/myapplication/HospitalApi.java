package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HospitalApi {
    @GET("hospital.php") // PHP API 경로
    Call<List<Hospital>> getHospitals(@Query("h_region") String region); // h_region을 쿼리 파라미터로 전송
}