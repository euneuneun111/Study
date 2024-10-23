package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HospitalApi {
    @GET("hospital.php")
    Call<List<Hospital>> getHospitals(@Query("h_region") String region);
}