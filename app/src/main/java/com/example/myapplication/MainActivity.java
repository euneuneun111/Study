package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecentRecordAdapter adapter;
    private RecentRecordViewModel viewModel;
    private List<RecentRecord> recentRecordList;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String imageUri = result.getData().getStringExtra("imageUri");
                    if (imageUri != null) {
                        addRecentRecord(imageUri);
                    } else {
                        Log.e("MainActivity", "Received imageUri is null");
                    }
                } else {
                    Log.e("MainActivity", "Result is not OK or data is null");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView 및 데이터 리스트 초기화
        recyclerView = findViewById(R.id.recent_records_recycler_view);
        recentRecordList = new ArrayList<>();

        // ViewModel 초기화
        viewModel = new ViewModelProvider(this).get(RecentRecordViewModel.class);
        setupObservers(); // LiveData 관찰 설정

        // UI 컴포넌트 초기화
        setupUIComponents();

        // Adapter 및 RecyclerView 설정
        setupRecyclerView();

        // 최근 기록 로드
        loadRecentRecords();

    }

    private void setupObservers() {
        viewModel.getRecentRecords().observe(this, records -> {
            recentRecordList.clear();
            recentRecordList.addAll(records);
            adapter.notifyDataSetChanged(); // 어댑터 갱신
        });
    }

    private void setupUIComponents() {
        LinearLayout MRI_add = findViewById(R.id.mri_add);
        MRI_add.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            resultLauncher.launch(intent);
        });

        ImageView directory_disease = findViewById(R.id.disease_list);
        directory_disease.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DiseaselistActivity.class);
            startActivity(intent);
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

    private void setupRecyclerView() {
        adapter = new RecentRecordAdapter(recentRecordList, null); // 클릭 리스너 제거
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void addRecentRecord(String imageUri) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        RecentRecord recentRecord = new RecentRecord(currentTime, imageUri);
        viewModel.addRecentRecord(recentRecord);
        Log.d("MainActivity", "Added record: " + recentRecord);
    }

    private void loadRecentRecords() {
        // ViewModel에서 데이터를 관찰하므로 필요하지 않을 수 있습니다.
        // 데이터 초기 로딩 로직이 필요한 경우 해당 로직을 추가할 수 있습니다.
    }


}
