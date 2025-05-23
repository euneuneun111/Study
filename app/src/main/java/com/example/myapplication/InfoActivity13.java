package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity13 extends AppCompatActivity {

    private TextView diseaseNameTextView;
    private TextView diseaseDescriptionTextView;
    private TextView diseaseSymptomsTextView;
    private TextView diseaseFoodTextView;
    private TextView diseaseDepartmentTextView;
    private TextView diseaseCauseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info13);

        diseaseNameTextView = findViewById(R.id.disease_name213);
        diseaseDescriptionTextView = findViewById(R.id.disease_description213);
        diseaseSymptomsTextView = findViewById(R.id.disease_food213);
        diseaseFoodTextView = findViewById(R.id.disease_department213);
        diseaseDepartmentTextView = findViewById(R.id.disease2213);
        diseaseCauseTextView = findViewById(R.id.disease4213);


        // 확인 버튼 클릭 이벤트 처리
        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();  // 현재 액티비티 종료
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_board) {
                startActivity(new Intent(InfoActivity13.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(InfoActivity13.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(InfoActivity13.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
}
