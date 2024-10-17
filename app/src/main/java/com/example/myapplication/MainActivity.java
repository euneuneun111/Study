package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recentRecordRecyclerView;
    private RecentRecordAdapter recentRecordAdapter;
    private List<RecentRecord> recentRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 컴포넌트 초기화
        LinearLayout MRI_add = findViewById(R.id.mri_add);
        ImageView directory_disease = findViewById(R.id.disease_list);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        recentRecordRecyclerView = findViewById(R.id.recent_records_recycler_view);

        // RecyclerView 설정
        recentRecordRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 최근 기록 리스트 초기화
        initializeRecentRecords();

        // 어댑터 초기화 및 연결
        recentRecordAdapter = new RecentRecordAdapter(recentRecordList, recentRecord -> {
            // 최근 기록 항목 클릭 시 ResultActivity로 이동
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("imageUri", recentRecord.getImageUri().toString());
            intent.putExtra("timestamp", recentRecord.getTimestamp());
            startActivity(intent);
        });
        recentRecordRecyclerView.setAdapter(recentRecordAdapter);  // 어댑터 설정
        recentRecordAdapter.notifyDataSetChanged();  // 데이터 변경 알림

        // MRI_add 버튼 클릭 시 ResultActivity로 이동
        MRI_add.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(intent);
        });

        // 질병 목록 아이콘 클릭 시 DiseaselistActivity로 이동
        directory_disease.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DiseaselistActivity.class)));

        // BottomNavigationView 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_board) {
                startActivity(new Intent(MainActivity.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                return true;
            }
            return false;
        });
    }

    // 최근 기록 리스트 초기화
    private void initializeRecentRecords() {
        recentRecordList = new ArrayList<>();

        // 예시 데이터 추가 (실제 데이터로 교체 가능)

        // 어댑터가 null이 아닌 경우 데이터 변경 알림
        if (recentRecordAdapter != null) {
            Log.d("MainActivity", "Adapter notified of data change.");
            recentRecordAdapter.notifyDataSetChanged();  // 데이터 변경 알림
            Toast.makeText(MainActivity.this, "업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
