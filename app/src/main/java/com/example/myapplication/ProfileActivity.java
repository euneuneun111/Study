package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences; // SharedPreferences 임포트 추가
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile_nickname, profile_id, profile_doc, profile_re;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    startActivity(new Intent(ProfileActivity.this, BoardActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    return true;
                }
                return false;
            }
        });
    }
}
