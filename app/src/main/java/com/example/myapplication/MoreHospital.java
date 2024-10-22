package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoreHospital extends AppCompatActivity {

    private RecyclerView hospitalView;
    private HospitalAdapter hospitalAdapter;
    private ArrayList<Hospital> hospitalList;
    private Spinner regionSpinner;
    private HospitalApi hospitalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_hospital);

        // RecyclerView 및 Spinner 초기화
        hospitalView = findViewById(R.id.hospital_view);
        regionSpinner = findViewById(R.id.regionSpinner);
        hospitalList = new ArrayList<>();
        hospitalAdapter = new HospitalAdapter(hospitalList);

        hospitalView.setLayoutManager(new LinearLayoutManager(this));
        hospitalView.setAdapter(hospitalAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.158/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create(gson)) // lenient mode 활성화
                .build();

        hospitalApi = retrofit.create(HospitalApi.class); // HospitalApi 인터페이스 생성

        setupSpinner(); // 스피너 초기화
    }

    private void setupSpinner() {
        // 스피너를 배열 리소스와 연결
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);

        // 스피너 항목 선택 시 이벤트 처리
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRegion = parent.getItemAtPosition(position).toString();

                // "지역 선택"을 선택한 경우 데이터 요청을 하지 않음
                if (!selectedRegion.equals("지역 선택")) {
                    fetchHospitals(selectedRegion); // 선택된 지역으로 병원 정보 요청
                } else {
                    hospitalList.clear();
                    hospitalAdapter.notifyDataSetChanged();  // 스피너가 '지역 선택'일 때 목록 초기화
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void fetchHospitals(String h_region) {
        // Retrofit을 이용한 병원 데이터 요청
        Call<List<Hospital>> call = hospitalApi.getHospitals(h_region);
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 성공적으로 데이터를 받아왔을 때
                    List<Hospital> hospitals = response.body();
                    Log.d("API Response", "Response: " + hospitals.toString());
                } else {
                    // 오류 응답을 디버깅하기 위해 응답 본문을 출력
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
