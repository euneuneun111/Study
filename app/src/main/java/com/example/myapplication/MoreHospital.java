package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MoreHospital extends AppCompatActivity {

    private RecyclerView hospitalView;
    private HospitalAdapter hospitalAdapter;
    private Spinner regionSpinner;
    private HospitalApi hospitalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_hospital);

        // RecyclerView 및 Spinner 초기화
        hospitalView = findViewById(R.id.hospital_view);
        regionSpinner = findViewById(R.id.regionSpinner);
        hospitalAdapter = new HospitalAdapter(); // 초기화 시 빈 리스트 사용

        hospitalView.setLayoutManager(new LinearLayoutManager(this));
        hospitalView.setAdapter(hospitalAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.158/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create(gson)) // lenient mode 활성화
                .build();

        hospitalApi = retrofit.create(HospitalApi.class);
        setupSpinner();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_board) {
                startActivity(new Intent(MoreHospital.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(MoreHospital.this, MainActivity.class));

                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(MoreHospital.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRegion = parent.getItemAtPosition(position).toString();

                if (!selectedRegion.equals("지역 선택")) {
                    fetchHospitals(selectedRegion);
                } else {
                    hospitalAdapter.updateData(new ArrayList<>()); // 지역 선택 시 데이터 초기화
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void fetchHospitals(String h_region) {
        Call<List<Hospital>> call = hospitalApi.getHospitals(h_region);  // List<Hospital> 타입으로 API 호출
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hospital> hospitalList = response.body(); // JSON 데이터 리스트 저장
                    Log.d("API Response", "Raw Response: " + hospitalList);

                    // 어댑터에 데이터 업데이트
                    hospitalAdapter.updateData(hospitalList);
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("API Error", "Error Response: " + errorResponse);
                    } catch (Exception e) {
                        Log.e("API Error", "Failed to parse error response: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                Log.e("RetrofitError", "Error: " + t.getMessage());
            }
        });
    }
}
