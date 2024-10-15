package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.NetworkClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoActivity extends AppCompatActivity {

    private TextView diseaseNameTextView;
    private TextView diseaseProbabilityTextView;
    private TextView diseaseDescriptionTextView;
    private TextView diseaseFoodTextView;
    private TextView diseaseDepartmentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // UI 요소 초기화
        diseaseNameTextView = findViewById(R.id.disease_name);
        diseaseProbabilityTextView = findViewById(R.id.disease_probability);
        diseaseDescriptionTextView = findViewById(R.id.disease_description);
        diseaseFoodTextView = findViewById(R.id.disease_food);
        diseaseDepartmentTextView = findViewById(R.id.disease_department);

        // Intent로부터 전달된 데이터 수신
        Intent intent = getIntent();
        String diseaseName = intent.getStringExtra("diseaseName");
        String similarity = intent.getStringExtra("similarity");

        // 병명과 유사성 정보가 유효할 때만 처리
        if (diseaseName != null && similarity != null) {
            // 병명 설정
            diseaseNameTextView.setText(diseaseName + " 증상이 의심됩니다.");
            diseaseProbabilityTextView.setText("유사성: " + similarity + "%");

            // DB에서 병명에 해당하는 질병 정보 가져오기
            fetchDiseaseInfoFromDB(diseaseName);
        } else {
            // 병명 또는 유사성이 없을 경우 예외 처리
            Toast.makeText(this, "질병 정보가 없습니다.", Toast.LENGTH_LONG).show();
        }

        // BottomNavigationView 초기화 (사용하지 않는다면 이 부분은 삭제 가능)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 선택한 메뉴에 따라 동작 구현 가능
                return true;
            }
        });
    }

    // 서버에서 질병 정보 가져오기 (병명에 해당하는 정보만 가져오기)
    private void fetchDiseaseInfoFromDB(String diseaseName) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();  // NetworkClient에서 Retrofit 인스턴스 가져오기

        ApiService apiService = retrofit.create(ApiService.class);

        // 병명에 해당하는 질병 정보만 가져오는 API 요청
        Call<Disease> call = apiService.getDiseaseByName(diseaseName);

        call.enqueue(new Callback<Disease>() {
            @Override
            public void onResponse(Call<Disease> call, Response<Disease> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Disease disease = response.body();

                    // UI에 질병 정보 반영
                    diseaseDescriptionTextView.setText(disease.getDefinition());
                    diseaseFoodTextView.setText(disease.getEat());
                    diseaseDepartmentTextView.setText(disease.getMedicalDepartment());
                } else {
                    // response가 성공적이지 않거나 body가 null인 경우에 대한 처리
                    Toast.makeText(InfoActivity.this, "질병 정보를 가져오는데 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Disease> call, Throwable t) {
                // 오류 처리
                Toast.makeText(InfoActivity.this, "질병 정보를 불러오지 못했습니다. 네트워크를 확인하세요.", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
