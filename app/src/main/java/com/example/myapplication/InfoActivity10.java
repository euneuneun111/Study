package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity10 extends AppCompatActivity {

    private TextView diseaseNameTextView;
    private TextView diseaseDescriptionTextView;
    private TextView diseaseSymptomsTextView;
    private TextView diseaseFoodTextView;
    private TextView diseaseDepartmentTextView;
    private TextView diseaseCauseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info10);

        diseaseNameTextView = findViewById(R.id.disease_name210);
        diseaseDescriptionTextView = findViewById(R.id.disease_description210);
        diseaseSymptomsTextView = findViewById(R.id.disease_food210);
        diseaseFoodTextView = findViewById(R.id.disease_department210);
        diseaseDepartmentTextView = findViewById(R.id.disease2210);
        diseaseCauseTextView = findViewById(R.id.disease4210);


        // 확인 버튼 클릭 이벤트 처리
        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 확인 버튼 클릭 시 동작 (예: 액티비티 종료 또는 다른 화면으로 이동)
                Toast.makeText(InfoActivity10.this, "", Toast.LENGTH_SHORT).show();
                finish();  // 현재 액티비티 종료
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_board) {
                startActivity(new Intent(InfoActivity10.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(InfoActivity10.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(InfoActivity10.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
}
