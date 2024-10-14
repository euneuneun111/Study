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


        Button Okaybutton = findViewById(R.id.confirm_button);

        Okaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 실행할 작업을 여기에 작성

                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    startActivity(new Intent(InfoActivity.this, BoardActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(InfoActivity.this,   MainActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(InfoActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });

        // UI 요소 초기화
        diseaseNameTextView = findViewById(R.id.disease_name);
        diseaseProbabilityTextView = findViewById(R.id.disease_probability);
        diseaseDescriptionTextView = findViewById(R.id.disease_description);
        diseaseFoodTextView = findViewById(R.id.disease_food);
        diseaseDepartmentTextView = findViewById(R.id.disease_department);
        hospital1ImageView = findViewById(R.id.hospital_1);
        hospital2ImageView = findViewById(R.id.hospital_2);
        hospital3ImageView = findViewById(R.id.hospital_3);

        // Intent로부터 데이터 수신
        Intent intent = getIntent();
        String diseaseName = intent.getStringExtra("diseaseName");
        String diseaseProbability = intent.getStringExtra("diseaseProbability");
        String diseaseDescription = intent.getStringExtra("diseaseDescription");
        String diseaseFood = intent.getStringExtra("diseaseFood");
        String diseaseDepartment = intent.getStringExtra("diseaseDepartment");

        // UI에 데이터 설정
        diseaseNameTextView.setText(diseaseName);
        diseaseProbabilityTextView.setText(diseaseProbability);
        diseaseDescriptionTextView.setText(diseaseDescription);
        diseaseFoodTextView.setText(diseaseFood);
        diseaseDepartmentTextView.setText(diseaseDepartment);


        // 또는 URI로 설정할 경우:
        // Uri hospital1Uri = Uri.parse(intent.getStringExtra("hospital1Uri"));
        // hospital1ImageView.setImageURI(hospital1Uri);
    }
}
