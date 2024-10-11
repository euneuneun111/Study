package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResultActivity extends AppCompatActivity {

    private ImageView resultImageView;
    private TextView resultTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Intent로부터 데이터 가져오기
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        String predictionResult = intent.getStringExtra("predictionResult");

        // 이미지 URI를 Uri 객체로 변환
        Uri imageUri = Uri.parse(imageUriString);

        // 이미지 표시
        Glide.with(this)
                .load(imageUri)
                .into(resultImageView);

        // 예측 결과 표시
        resultTextView.setText(predictionResult);

        LinearLayout infoButton = findViewById(R.id.mri_check);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 실행할 작업을 여기에 작성

                Intent intent = new Intent(ResultActivity.this,InfoActivity.class);
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
                    startActivity(new Intent(ResultActivity.this, BoardActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(ResultActivity.this, ClassifyActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(ResultActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
