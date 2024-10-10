package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 골프 마사지 버튼 클릭 리스너
        ImageButton buttonGolf = findViewById(R.id.button_golf);
        buttonGolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Golf Massage 버튼 클릭됨", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, LungActivity.class);
                startActivity(intent);
            }

        });

        // 뇌질환 버튼 클릭 리스너
        ImageButton buttonBrain = findViewById(R.id.button_brain);
        buttonBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Brain Disease 버튼 클릭됨", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, BrainActivity.class);
                startActivity(intent);
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 리스너 추가하여 메뉴 선택 시 동작 정의
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_book) {
                    // 책 아이템 클릭 시 처리
                    return true;
                } else if (id == R.id.nav_home) {
                    // 홈 아이템 클릭 시 처리
                    return true;
                } else if (id == R.id.nav_profile) {
                    // 프로필 아이템 클릭 시 ProfileActivity로 전환
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


    }
}
