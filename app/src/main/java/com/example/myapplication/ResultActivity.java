package com.example.myapplication;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;  // 이미지를 선택하는 요청 코드
    private ImageView resultImageView;
    private TextView resultTextView;
    private Interpreter tflite;  // TensorFlow Lite 모델 인터프리터
    private String[] classNames = new String[15];  // 클래스 이름 배열
    private String[] diseaseDescriptions = new String[15];  // 질병 설명 배열
    private Prediction[] predictions;  // 예측 결과 배열
    private ArrayList<RecentRecord> recentRecords = new ArrayList<>();  // 최근 기록 저장 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultImageView = findViewById(R.id.result_image_view);
        resultTextView = findViewById(R.id.result_text_view);

        // 클래스 이름 및 질병 설명 초기화
        initializeClassNames();
        initializeDiseaseDescriptions();

        // TensorFlow Lite 모델 초기화
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            Log.e("ResultActivity", "TensorFlow Lite 모델 초기화 중 오류 발생", e);
        }

        // Intent로부터 전달받은 이미지 URI와 예측 결과 표시
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        String predictionResult = intent.getStringExtra("predictionResult");

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            Glide.with(this).load(imageUri).into(resultImageView);
            resultTextView.setText(predictionResult);
        }

        // 갤러리에서 이미지 선택을 위한 버튼
        LinearLayout uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

                TextView resultTextView = findViewById(R.id.result_text_view);
                LinearLayout mriCheckLayout = findViewById(R.id.mri_check);

                resultTextView.setVisibility(View.VISIBLE); // TextView를 VISIBLE로 설정
                mriCheckLayout.setVisibility(View.VISIBLE); // LinearLayout을 VISIBLE로 설정
            }
        });

        // 결과 확인 버튼 클릭 시 InfoActivity로 이동
        LinearLayout checkResultButton = findViewById(R.id.mri_check);
        checkResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (predictions != null && predictions.length > 0) {
                    String bestPrediction = predictions[0].className;
                    float bestSimilarity = predictions[0].probability * 100; // 확률을 백분율로 변환

                    // InfoActivity로 이동하며 병명만 전달, 병명에 맞는 정보는 InfoActivity에서 처리
                    Intent intent = new Intent(ResultActivity.this, InfoActivity.class);
                    intent.putExtra("diseaseName", bestPrediction);  // 병명을 전달
                    intent.putExtra("similarity", String.format("%.2f", bestSimilarity)); // 유사성 전달
                    startActivity(intent);


                } else {
                    Toast.makeText(ResultActivity.this, "이미지를 먼저 분석하세요.", Toast.LENGTH_SHORT).show();
                }
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
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(ResultActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    // 갤러리 열기 메서드
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    // 선택한 이미지 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    // 선택한 이미지를 비트맵으로 변환
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    Bitmap enhancedBitmap = enhanceImageQuality(bitmap);  // 이미지를 224x224로 크기 조정

                    // 분석 결과 생성 (TensorFlow Lite 모델을 사용해 분석)
                    String predictionResult = classifyImage(enhancedBitmap);

                    // 이미지와 분석 결과 표시
                    Glide.with(this).load(selectedImageUri).into(resultImageView);
                    resultTextView.setText(predictionResult);

                    // 선택한 이미지 URI와 분석 결과를 기록
                    saveRecentRecord(selectedImageUri.toString(), predictionResult);

                    // 선택한 이미지 URI를 Intent로 전달
                    Intent intent = new Intent(ResultActivity.this, MainActivity.class); // 다음 Activity로 변경
                    intent.putExtra("imageUri", selectedImageUri.toString());
                    intent.putExtra("predictionResult", predictionResult);



                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ResultActivity", "이미지 로딩 중 오류 발생", e);
                }
            }
        }
    }

    // 최근 기록 저장 메서드
    private void saveRecentRecord(String imageUri, String predictionResult) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        RecentRecord recentRecord = new RecentRecord(currentTime, imageUri);
        recentRecords.add(recentRecord);

        // 로그로 기록 저장 확인
        Log.d("ResultActivity", "저장된 기록: " + recentRecord.getTimestamp() + ", " + recentRecord.getImageUri());
    }

    private Bitmap enhanceImageQuality(Bitmap bitmap) {
        // 이미지를 224x224로 크기 조정
        return Bitmap.createScaledBitmap(bitmap, 224, 224, true);
    }

    private String classifyImage(Bitmap bitmap) {
        if (tflite == null) {
            Log.e("TFLite", "모델이 초기화되지 않았습니다.");
            return "모델이 초기화되지 않았습니다.";
        }

        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
        inputBuffer.order(java.nio.ByteOrder.nativeOrder());

        int[] intValues = new int[224 * 224];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int pixelValue : intValues) {
            inputBuffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f); // Red
            inputBuffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);  // Green
            inputBuffer.putFloat((pixelValue & 0xFF) / 255.0f);          // Blue
        }

        float[][] output = new float[1][15];
        tflite.run(inputBuffer, output);

        predictions = new Prediction[15];
        for (int i = 0; i < 15; i++) {
            predictions[i] = new Prediction(classNames[i], output[0][i]);
        }

        // 확률에 따라 예측 결과를 정렬
        Arrays.sort(predictions, Comparator.comparing(Prediction::getProbability).reversed());

        // 상위 5개의 예측 결과 표시
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            resultBuilder.append(predictions[i].className)
                    .append(" (")
                    .append(String.format(Locale.getDefault(), "%.2f", predictions[i].probability * 100))
                    .append("%)\n")
                    .append(diseaseDescriptions[i])
                    .append("\n\n");
        }

        return resultBuilder.toString();
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("Real3.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 클래스 이름 초기화
    private void initializeClassNames() {
        classNames[0] = "췌장암";
        classNames[1] = "신장암";
        classNames[2] = "위암";
        // ... (다른 클래스 이름 초기화)
    }

    // 질병 설명 초기화
    private void initializeDiseaseDescriptions() {
        diseaseDescriptions[0] = "췌장암 설명";
        diseaseDescriptions[1] = "신장암 설명";
        diseaseDescriptions[2] = "위암 설명";
        // ... (다른 질병 설명 초기화)
    }

    private static class Prediction {
        String className;
        float probability;

        public Prediction(String className, float probability) {
            this.className = className;
            this.probability = probability;
        }

        public float getProbability() {
            return probability;
        }
    }
}
