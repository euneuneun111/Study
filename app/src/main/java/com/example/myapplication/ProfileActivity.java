package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity{

    private TextView profile_nickname, profile_id, profile_doc, profile_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_profile);

        ImageView Backview = findViewById(R.id.iv_arrow_left_board);

        profile_nickname = findViewById(R.id.profile_nickname);
        profile_id = findViewById(R.id.profile_id);
        profile_doc = findViewById(R.id.profile_doc);
        profile_re = findViewById(R.id.profile_re);

        // LoginActivity로부터 전달받은 사용자 정보 추출 및 설정
        String nickname = getIntent().getStringExtra("profile_nickname");
        String _id = getIntent().getStringExtra("profile_id");
        String doc = getIntent().getStringExtra("profile_doc");
        String re = getIntent().getStringExtra("profile_re");

        profile_nickname.setText("NickName: " + profile_nickname);
        profile_id.setText("Id: " + profile_id);
        profile_doc.setText("Doctor: " + profile_doc);
        profile_re.setText("Region: " + profile_re);

        Backview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 실행할 작업을 여기에 작성

                // 예: 뒤로 가기 동작 구현
                finish();  // 현재 액티비티를 종료하고 이전 액티비티로 돌아갑니다.
            }
        });

        TextView Morehospital = findViewById(R.id.hospital_more);

        Morehospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 실행할 작업을 여기에 작성

                startActivity(new Intent(ProfileActivity.this, MoreHospital.class));
            }
        });


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
