package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity11 extends AppCompatActivity {

    private TextView diseaseNameTextView;
    private TextView diseaseDescriptionTextView;
    private TextView diseaseSymptomsTextView;
    private TextView diseaseFoodTextView;
    private TextView diseaseDepartmentTextView;
    private TextView diseaseCauseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info11);

        diseaseNameTextView = findViewById(R.id.disease_name211);
        diseaseDescriptionTextView = findViewById(R.id.disease_description211);
        diseaseSymptomsTextView = findViewById(R.id.disease_food211);
        diseaseFoodTextView = findViewById(R.id.disease_department211);
        diseaseDepartmentTextView = findViewById(R.id.disease2211);
        diseaseCauseTextView = findViewById(R.id.disease4211);


        // 확인 버튼 클릭 이벤트 처리
        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 확인 버튼 클릭 시 동작 (예: 액티비티 종료 또는 다른 화면으로 이동)
                Toast.makeText(InfoActivity11.this, "", Toast.LENGTH_SHORT).show();
                finish();  // 현재 액티비티 종료
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_board) {
                startActivity(new Intent(InfoActivity11.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(InfoActivity11.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(InfoActivity11.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
}
