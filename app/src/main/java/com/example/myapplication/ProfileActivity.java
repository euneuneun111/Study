package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences; // SharedPreferences 임포트 추가
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile_nickname, profile_id, profile_doc, profile_re, h_name, h_contact_number, h_address, h_web_server, h_reservation_link;
    private HospitalApi hospitalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_profile);

        // View 초기화
        ImageView Backview = findViewById(R.id.iv_arrow_left_board);
        profile_nickname = findViewById(R.id.profile_nickname);
        profile_id = findViewById(R.id.profile_id);
        profile_doc = findViewById(R.id.profile_doc);
        profile_re = findViewById(R.id.profile_re);
        h_name = findViewById(R.id.h_name);
        h_contact_number = findViewById(R.id.h_contact_number);
        h_address = findViewById(R.id.h_address);
        h_web_server = findViewById(R.id.h_web_server);
        h_reservation_link = findViewById(R.id.h_reservation_link);

        // Shared Preferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String nickname = sharedPreferences.getString("u_nickname", "Unknown");
        String id = sharedPreferences.getString("u_id", "Unknown");
        String doc = sharedPreferences.getString("u_doctor", "Unknown");
        String region = sharedPreferences.getString("u_region", "Unknown");

        // 화면에 유저 정보 표시
        profile_nickname.setText("NickName: " + nickname);
        profile_id.setText("Id: " + id);
        profile_doc.setText("Doctor: " + doc);
        profile_re.setText("Region: " + region);

        Backview.setOnClickListener(v -> finish());

        TextView Morehospital = findViewById(R.id.hospital_more);
        Morehospital.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MoreHospital.class)));

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.158/")  // 서버 주소
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        hospitalApi = retrofit.create(HospitalApi.class);

        // SharedPreferences에서 사용자의 지역 정보 가져오기
        loadHospitalData(region);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int menuItemId = item.getItemId();
            if (menuItemId == R.id.nav_board) {
                startActivity(new Intent(ProfileActivity.this, BoardActivity.class));
                return true;
            } else if (menuItemId == R.id.nav_home) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                return true;
            } else if (menuItemId == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }

    // 병원 정보 불러오는 메소드
    private void loadHospitalData(String region) {
        Call<List<Hospital>> call = hospitalApi.getHospitals(region);  // API 호출
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Hospital hospital = response.body().get(0);
                    h_name.setText(hospital.getH_name());
                    h_contact_number.setText(hospital.getH_contact_number());
                    h_address.setText(hospital.getH_address());
                    h_web_server.setText(hospital.getH_web_server());
                    h_reservation_link.setText(hospital.getH_reservation_link());
                } else {
                    Log.e("ProfileActivity", "해당 지역의 병원 정보를 찾을 수 없습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                Log.e("ProfileActivity", "병원 정보 불러오기 실패", t);
            }
        });
    }
}
