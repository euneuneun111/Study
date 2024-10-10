package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LungActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lung);

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 리스너 추가하여 메뉴 선택 시 동작 정의
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    Intent intent = new Intent(LungActivity.this, BoardActivity.class);
                    startActivity(intent);
                    // 책 아이템 클릭 시 처리
                    return true;
                } else if (id == R.id.nav_home) {
                    Intent intent = new Intent(LungActivity.this, MainActivity.class);
                    startActivity(intent);
                    // 홈 아이템 클릭 시 처리
                    return true;
                } else if (id == R.id.nav_profile) {
                    // 프로필 아이템 클릭 시 ProfileActivity로 전환
                    Intent intent = new Intent(LungActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }
}
