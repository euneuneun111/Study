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

    private static final int PICK_IMAGE = 1; // 이미지를 선택하기 위한 요청 코드
    private ImageView resultImageView;
    private TextView resultTextView;
    private Interpreter tflite; // TensorFlow Lite 모델 인터프리터
    private String[] classNames = new String[15]; // 클래스 이름 배열
    private ArrayList<RecentRecord> recentRecords = new ArrayList<>(); // 최근 기록 목록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();
        initializeModel();

        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        String predictionResult = intent.getStringExtra("predictionResult");

        if (imageUriString != null) {
            displayResult(Uri.parse(imageUriString), predictionResult);
        }

        setupListeners();
        setupBottomNavigation();
    }

    private void initViews() {
        resultImageView = findViewById(R.id.result_image_view);
        resultTextView = findViewById(R.id.result_text_view);
        LinearLayout uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setVisibility(View.VISIBLE);
    }

    private void initializeModel() {
        try {
            tflite = new Interpreter(loadModelFile());
            initializeClassNames();
        } catch (IOException e) {
            Log.e("ResultActivity", "TensorFlow Lite 모델 초기화 오류", e);
        }
    }

    private void setupListeners() {
        findViewById(R.id.uploadButton).setOnClickListener(v -> {
            openGallery(); // 갤러리를 엽니다.
            v.setVisibility(View.GONE); // 업로드 버튼 숨김
            findViewById(R.id.applyButton).setVisibility(View.VISIBLE); // 적용 버튼 보이기
            findViewById(R.id.mri_check).setVisibility(View.VISIBLE); // MRI 체크 버튼 보이기
        });

        findViewById(R.id.applyButton).setOnClickListener(v -> {
            saveCurrentRecord(); // 현재 기록 저장
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            String currentImageUri = resultImageView.getTag().toString();
            intent.putExtra("imageUri", currentImageUri); // 이미지 URI 추가
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            intent.putExtra("timestamp", currentTime); // 타임스탬프 추가
            startActivity(intent); // MainActivity로 이동
            finish(); // 현재 액티비티 종료 1111
        });

        findViewById(R.id.mri_check).setOnClickListener(v -> {
            // MRI 체크 버튼의 기능 구현
            Log.d("ResultActivity", "MRI 체크 버튼이 클릭되었습니다.");
            // MRI 체크 기능을 여기에 추가합니다.
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_board) {
                    startActivity(new Intent(ResultActivity.this, BoardActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_profile) {
                    startActivity(new Intent(ResultActivity.this, ProfileActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    // 갤러리를 열어 이미지를 선택합니다.
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    // 갤러리에서의 결과를 처리합니다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                processSelectedImage(selectedImageUri);
            }
        }
    }

    private void processSelectedImage(Uri selectedImageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            Bitmap enhancedBitmap = enhanceImageQuality(bitmap); // 이미지 품질 향상

            String predictionResult = classifyImage(enhancedBitmap);
            displayResult(selectedImageUri, predictionResult);
            saveRecentRecord(selectedImageUri.toString(), predictionResult);
        } catch (IOException e) {
            Log.e("ResultActivity", "이미지 로드 오류", e);
        }
    }

    private void displayResult(Uri imageUri, String predictionResult) {
        Glide.with(this).load(imageUri).into(resultImageView);
        resultTextView.setText(predictionResult);
        resultImageView.setTag(imageUri.toString()); // 이미지 URI를 태그로 설정
    }

    private void saveCurrentRecord() {
        String currentImageUri = resultImageView.getTag().toString();
        String currentPredictionResult = resultTextView.getText().toString();
        saveRecentRecord(currentImageUri, currentPredictionResult);
        Log.d("ResultActivity", "메인 화면에 기록이 저장되었습니다.");
    }

    private void saveRecentRecord(String imageUri, String predictionResult) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        RecentRecord recentRecord = new RecentRecord(currentTime, imageUri); // 타임스탬프, 이미지 URI 및 예측 결과 저장
        recentRecords.add(recentRecord);
        Log.d("ResultActivity", "저장된 기록: " + recentRecord.getTimestamp() + ", " + recentRecord.getImageUri());
    }

    private Bitmap enhanceImageQuality(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 224, 224, true); // 이미지를 224x224로 크기 조정
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
            inputBuffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f); // 빨강
            inputBuffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);  // 초록
            inputBuffer.putFloat((pixelValue & 0xFF) / 255.0f);          // 파랑
        }

        float[][] output = new float[1][15];
        tflite.run(inputBuffer, output);

        Prediction[] predictions = new Prediction[15];
        for (int i = 0; i < 15; i++) {
            predictions[i] = new Prediction(classNames[i], output[0][i]);
        }

        Arrays.sort(predictions, Comparator.comparing(Prediction::getProbability).reversed());
        return formatPredictionResults(predictions);
    }

    private String formatPredictionResults(Prediction[] predictions) {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < Math.min(5, predictions.length); i++) {
            resultBuilder.append(predictions[i].getClassName())
                    .append(": ")
                    .append(String.format("%.2f", predictions[i].getProbability() * 100))
                    .append("%\n");
        }
        return resultBuilder.toString();
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("your_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void initializeClassNames() {
        // 클래스 이름 배열 초기화
        classNames[0] = "Class1";
        classNames[1] = "Class2";
        classNames[2] = "Class3";
        classNames[3] = "Class4";
        classNames[4] = "Class5";
        classNames[5] = "Class6";
        classNames[6] = "Class7";
        classNames[7] = "Class8";
        classNames[8] = "Class9";
        classNames[9] = "Class10";
        classNames[10] = "Class11";
        classNames[11] = "Class12";
        classNames[12] = "Class13";
        classNames[13] = "Class14";
        classNames[14] = "Class15";
    }
    private static class Prediction {
        private String className;
        private float probability;

        public Prediction(String className, float probability) {
            this.className = className;
            this.probability = probability;
        }

        public String getClassName() {
            return className;
        }

        public float getProbability() {
            return probability;
        }
    }
}

