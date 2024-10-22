package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView[] imageViews = new ImageView[4];
    private TextView[] timestampViews = new TextView[4];
    private String[] imageUris = new String[4]; // 이미지 URI 저장
    private int currentIndex;  // 현재 이미지가 채워질 슬롯 인덱스

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String INDEX_KEY = "currentIndex"; // 저장할 인덱스 키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4개의 ImageView와 TextView 연결
        imageViews[0] = findViewById(R.id.image_view_1);
        imageViews[1] = findViewById(R.id.image_view_2);
        imageViews[2] = findViewById(R.id.image_view_3);
        imageViews[3] = findViewById(R.id.image_view_4);

        timestampViews[0] = findViewById(R.id.timestamp_view_1);
        timestampViews[1] = findViewById(R.id.timestamp_view_2);
        timestampViews[2] = findViewById(R.id.timestamp_view_3);
        timestampViews[3] = findViewById(R.id.timestamp_view_4);

        // SharedPreferences에서 저장된 currentIndex 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentIndex = sharedPreferences.getInt(INDEX_KEY, 0); // 0이 기본값

        // MRI 이미지를 선택하기 위한 버튼 클릭 리스너
        findViewById(R.id.mri_add).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(intent);  // 이미지를 선택하는 액티비티 시작
        });

        ImageView diseaseListImageView = findViewById(R.id.disease_list);
        diseaseListImageView.setOnClickListener(v -> {
            // DiseaselistActivity로 이동하는 Intent 생성
            Intent intent = new Intent(MainActivity.this, DiseaselistActivity.class);
            startActivity(intent); // 액티비티 시작
        });

        // 각 이미지 클릭 시 ResultActivity로 이미지 전송
        for (int i = 0; i < 4; i++) {
            int index = i;
            imageViews[i].setOnClickListener(v -> {
                if (imageUris[index] != null) {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("imageUri", imageUris[index]);  // 클릭한 이미지의 URI를 전송

                    // 병명 추출
                    String timestamp = timestampViews[index].getText().toString();
                    String predictionResult = timestamp.substring(timestamp.indexOf("(") + 1, timestamp.indexOf(")")); // 병명 추출

                    intent.putExtra("predictionResult", predictionResult); // 병명도 함께 전송
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 텍스트 클릭 리스너 추가
        // 각 이미지 클릭 시 병명에 따라 상세정보로 이동
        // 텍스트 클릭 리스너 추가
        for (int i = 0; i < 4; i++) {
            int index = i;
            timestampViews[i].setOnClickListener(v -> { // timestampViews 클릭 리스너
                if (imageUris[index] != null) {
                    // 타임스탬프에서 병명 추출
                    String timestamp = timestampViews[index].getText().toString();
                    String bestPrediction = timestamp.substring(timestamp.indexOf("(") + 1, timestamp.indexOf(")")); // 병명 추출

                    // Log 추가
                    Log.d("MainActivity", "Prediction Result: " + bestPrediction); // 병명 출력

                    Intent intent;
                    switch (bestPrediction) {
                        case "무기폐 (Atelectasis":
                            intent = new Intent(MainActivity.this, InfoActivity1.class);
                            break;
                        case "심장비대 (Cardiomegaly":
                            intent = new Intent(MainActivity.this, InfoActivity2.class);
                            break;
                        case "삼출 (Effusion":
                            intent = new Intent(MainActivity.this, InfoActivity3.class);
                            break;
                        case "침윤 (Infiltration":
                            intent = new Intent(MainActivity.this, InfoActivity4.class);
                            break;
                        case "종괴 (Mass":
                            intent = new Intent(MainActivity.this, InfoActivity5.class);
                            break;
                        case "결절 (Nodule":
                            intent = new Intent(MainActivity.this, InfoActivity6.class);
                            break;
                        case "폐렴 (Pneumonia":
                            intent = new Intent(MainActivity.this, InfoActivity7.class);
                            break;
                        case "기흉 (Pneumothorax":
                            intent = new Intent(MainActivity.this, InfoActivity8.class);
                            break;
                        case "폐경화 (Consolidation":
                            intent = new Intent(MainActivity.this, InfoActivity9.class);
                            break;
                        case "부종 (Edema":
                            intent = new Intent(MainActivity.this, InfoActivity10.class);
                            break;
                        case "폐기종 (Emphysema":
                            intent = new Intent(MainActivity.this, InfoActivity11.class);
                            break;
                        case "섬유화 (Fibrosis":
                            intent = new Intent(MainActivity.this, InfoActivity12.class);
                            break;
                        case "흉막 비후 (Pleural Thickening":
                            intent = new Intent(MainActivity.this, InfoActivity13.class);
                            break;
                        case "탈장 (Hernia":
                            intent = new Intent(MainActivity.this, InfoActivity14.class);
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                    }
                    startActivity(intent); // 해당 상세정보 액티비티로 이동
                } else {
                    Toast.makeText(MainActivity.this, "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }


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

        // SharedPreferences에서 저장된 이미지 불러오기
        loadImagesFromPreferences();
    }

    private void addRecentRecord(String imageUri, String timestamp, String bestPrediction) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 4개 이상의 이미지를 저장할 때 1번 배열을 삭제하고 나머지를 한 칸씩 앞으로 이동
        if (currentIndex >= 4) {
            for (int i = 0; i < 3; i++) {
                imageUris[i] = imageUris[i + 1];  // 이전 이미지 URI 이동
                timestampViews[i].setText(timestampViews[i + 1].getText());  // 타임스탬프 이동
                Uri uri = Uri.parse(imageUris[i]);
                Glide.with(this).load(uri).into(imageViews[i]);  // UI 업데이트

                // SharedPreferences에 이동된 이미지와 타임스탬프 저장
                editor.putString("imageUri_" + i, imageUris[i]);
                editor.putString("timestamp_" + i, timestampViews[i].getText().toString());
            }

            // 마지막 4번째 자리에 새로운 이미지 저장
            imageUris[3] = imageUri;
            timestampViews[3].setText("Time: " + timestamp);
            Uri uri = Uri.parse(imageUri);
            Glide.with(this).load(uri).into(imageViews[3]);

            // SharedPreferences에 새 이미지와 타임스탬프 저장
            editor.putString("imageUri_" + 3, imageUri);
            editor.putString("timestamp_" + 3, "Time: " + timestamp);
        } else {
            // currentIndex가 4 미만일 때는 현재 인덱스에 이미지를 저장
            imageUris[currentIndex] = imageUri;  // 이미지 URI 저장
            Uri uri = Uri.parse(imageUri);
            Glide.with(this).load(uri).into(imageViews[currentIndex]);
            timestampViews[currentIndex].setText("Time: " + timestamp);

            // SharedPreferences에 이미지와 타임스탬프 저장
            editor.putString("imageUri_" + currentIndex, imageUri);
            editor.putString("timestamp_" + currentIndex, "Time: " + timestamp);
            currentIndex++;
        }

        // currentIndex를 4로 고정
        if (currentIndex > 4) {
            currentIndex = 4;
        }

        // currentIndex 업데이트
        editor.putInt(INDEX_KEY, currentIndex);
        editor.apply();
    }

    // SharedPreferences에서 이미지와 타임스탬프 불러오기
    private void loadImagesFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        for (int i = 0; i < 4; i++) {
            String imageUri = sharedPreferences.getString("imageUri_" + i, null);
            String timestamp = sharedPreferences.getString("timestamp_" + i, null);

            if (imageUri != null && timestamp != null) {
                imageUris[i] = imageUri;
                Uri uri = Uri.parse(imageUri);
                Glide.with(this).load(uri).into(imageViews[i]);
                timestampViews[i].setText("Time: " + timestamp);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Intent에서 데이터 받기
        Intent intent = getIntent();
        String imageUri = intent.getStringExtra("imageUri");
        String timestamp = intent.getStringExtra("timestamp");
        String bestPrediction = intent.getStringExtra("predictionResult"); // 병명 추가

        // 데이터가 null이 아닌 경우 처리
        if (imageUri != null && timestamp != null) {
            addRecentRecord(imageUri, timestamp, bestPrediction); // 병명도 함께 전달

            // currentIndex를 SharedPreferences에 저장
            saveCurrentIndex(currentIndex);
        }

        Log.d("MainActivity", "Received intent with imageUri: " + imageUri + ", timestamp: " + timestamp + ", predictionResult: " + bestPrediction);
    }

    // SharedPreferences에 currentIndex 저장
    private void saveCurrentIndex(int index) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(INDEX_KEY, index);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause called");
    }
}
