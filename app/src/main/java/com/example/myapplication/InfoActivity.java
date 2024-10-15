package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity {

    private TextView diseaseNameTextView;
    private TextView diseaseProbabilityTextView;
    private TextView diseaseDescriptionTextView;
    private TextView diseaseFoodTextView;
    private TextView diseaseDepartmentTextView;
    private ImageView hospital1ImageView;
    private ImageView hospital2ImageView;
    private ImageView hospital3ImageView;

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
        hospital1ImageView = findViewById(R.id.hospital_1);
        hospital2ImageView = findViewById(R.id.hospital_2);
        hospital3ImageView = findViewById(R.id.hospital_3);

        // 확인 버튼을 누르면 MainActivity로 돌아감
        Button okayButton = findViewById(R.id.confirm_button);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    startActivity(new Intent(InfoActivity.this, BoardActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(InfoActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(InfoActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Intent로부터 데이터 수신
        Intent intent = getIntent();
        if (intent != null) {
            String diseaseName = intent.getStringExtra("diseaseName");
            String diseaseProbability = intent.getStringExtra("diseaseProbability");
            String diseaseDescription = intent.getStringExtra("diseaseDescription");
            String diseaseFood = intent.getStringExtra("diseaseFood");
            String diseaseDepartment = intent.getStringExtra("diseaseDepartment");

            // UI에 전달받은 데이터 설정 (null 체크 추가)
            diseaseNameTextView.setText(diseaseName != null ? diseaseName : "N/A");
            diseaseProbabilityTextView.setText(diseaseProbability != null ? diseaseProbability : "N/A");
            diseaseDescriptionTextView.setText(diseaseDescription != null ? diseaseDescription : "N/A");
            diseaseFoodTextView.setText(diseaseFood != null ? diseaseFood : "N/A");
            diseaseDepartmentTextView.setText(diseaseDepartment != null ? diseaseDepartment : "N/A");
        } else {
            // Intent가 null인 경우에 대비한 기본 처리
            diseaseNameTextView.setText("Unknown Disease");
            diseaseProbabilityTextView.setText("Unknown Probability");
            diseaseDescriptionTextView.setText("No Description Available");
            diseaseFoodTextView.setText("No Food Recommendations");
            diseaseDepartmentTextView.setText("No Department Information");
        }
    }
}
