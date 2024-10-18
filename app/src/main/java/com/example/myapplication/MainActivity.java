package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecentRecordAdapter adapter;
    private ArrayList<RecentRecord> recentRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recent_records_recycler_view);
        recentRecords = new ArrayList<>();

        // UI 컴포넌트 초기화
        LinearLayout MRI_add = findViewById(R.id.mri_add);

        // MRI_add 버튼 클릭 시 ResultActivity로 이동
        MRI_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                // 시작한 Activity에서 데이터를 반환받을 수 있도록 시작
                startActivityForResult(intent, 1);
            }
        });

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

        adapter = new RecentRecordAdapter(recentRecords, new RecentRecordAdapter.OnRecordClickListener() {
            @Override
            public void onRecordClick(RecentRecord record) {
                // 클릭 시의 동작을 정의합니다.
                // 예: 클릭한 기록의 URI로 새로운 액티비티를 시작하거나 상세 정보를 표시하는 기능을 추가할 수 있습니다.
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 데이터 로딩 및 업데이트
        loadRecentRecords();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // ResultActivity에서 이미지 URI를 반환한다고 가정합니다.
            String imageUri = data.getStringExtra("imageUri");
            if (imageUri != null) {
                addRecentRecord(imageUri); // 새 기록 추가 메서드 호출
            }
        }
    }

    private void loadRecentRecords() {
        // Intent로부터 최근 기록을 가져오는 방법을 구현하세요.
        ArrayList<RecentRecord> recordsFromIntent = (ArrayList<RecentRecord>) getIntent().getSerializableExtra("recentRecords");
        if (recordsFromIntent != null) {
            recentRecords.addAll(recordsFromIntent);
        }
        // 어댑터 데이터 갱신
        adapter.notifyDataSetChanged();
    }

    // RecentRecord 목록을 추가하는 메서드 (예시)
    public void addRecentRecord(String imageUri) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        RecentRecord recentRecord = new RecentRecord(currentTime, imageUri);
        recentRecords.add(recentRecord);
        adapter.notifyItemInserted(recentRecords.size() - 1); // 새로 추가된 아이템 알림
    }
}


