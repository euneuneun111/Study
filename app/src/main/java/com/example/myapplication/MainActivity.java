package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

        // MRI_add 버튼 클릭 시 ResultActivity로 이동
        MRI_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        // RecyclerView 설정
        recentRecordRecyclerView = findViewById(R.id.recent_records_recycler_view);
        recentRecordRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 최근 기록 리스트 초기화
        initializeRecentRecords();

        // 어댑터 설정 및 RecyclerView에 어댑터 적용
        recentRecordAdapter = new RecentRecordAdapter(recentRecordList, new RecentRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecentRecord recentRecord) {
                // 최근 기록 항목 클릭 시 ResultActivity로 이동
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("imageUri", recentRecord.getImageUri().toString());
                intent.putExtra("timestamp", recentRecord.getTimestamp());
                startActivity(intent);
            }
        });
        recentRecordRecyclerView.setAdapter(recentRecordAdapter);

        ImageView directory_disease = findViewById(R.id.disease_list);
        directory_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DiseaselistActivity.class));
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_board) {
                startActivity(new Intent(MainActivity.this, BoardActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void initializeRecentRecords() {
        // 예시 데이터를 추가
        recentRecordList = new ArrayList<>();
        recentRecordList.add(new RecentRecord(Uri.parse("content://example/uri1"),  "2024-10-16 10:30"));
        recentRecordList.add(new RecentRecord(Uri.parse("content://example/uri2"),  "2024-10-15 14:20"));
        // 실제 데이터로 교체 가능
    }
}

