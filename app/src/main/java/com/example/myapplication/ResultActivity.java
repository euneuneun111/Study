package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private Uri selectedImageUri;
    private static final int PICK_IMAGE = 1;  // 이미지를 선택하는 요청 코드
    private ImageView resultImageView;
    private TextView resultTextView;
    private Interpreter tflite;  // TensorFlow Lite 모델 인터프리터
    private String[] classNames = new String[15];  // 클래스 이름 배열
    private String[] diseaseDescriptions = new String[15];  // 질병 설명 배열
    private Prediction[] predictions;  // 예측 결과 배열
    private static final int REQUEST_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultImageView = findViewById(R.id.result_image_view);
        resultTextView = findViewById(R.id.result_text_view);

        // 권한 요청
        checkPermissions();

        // 클래스 이름 및 질병 설명 초기화
        initializeClassNames();
        initializeDiseaseDescriptions();

        // TensorFlow Lite 모델 초기화
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            Log.e("ResultActivity", "TensorFlow Lite 모델 초기화 중 오류 발생", e);
        }

        // Intent로부터 전달받은 이미지 URI 표시
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");

        if (imageUriString != null) {
            selectedImageUri = Uri.parse(imageUriString);
            Glide.with(this).load(selectedImageUri).into(resultImageView);

            // 이미지 분석 (TensorFlow Lite 모델 사용)
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                analyzeImage(bitmap);  // 이미지를 분석하는 메소드 호출a
            } catch (IOException e) {
                Log.e("ResultActivity", "이미지 로딩 중 오류 발생", e);
                Toast.makeText(this, "이미지를 처리하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        ImageView reimage = findViewById(R.id.result_image_view);

        reimage.setOnClickListener(v -> {
            openGallery();

        });

        LinearLayout uploadButton = findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(v -> {
            openGallery(); // 갤러리 열기
            v.setVisibility(View.GONE); // 업로드 버튼 숨기기
            findViewById(R.id.applyButton).setVisibility(View.VISIBLE); // 적용 버튼 보여주기
            findViewById(R.id.mri_check).setVisibility(View.VISIBLE); // MRI 체크 버튼 보여주기
        });

        // 저장 버튼 클릭 리스너 추가

        LinearLayout saveButton = findViewById(R.id.applyButton);
        saveButton.setOnClickListener(v -> {
            if (selectedImageUri != null && predictions != null && predictions.length > 0) {
                // SharedPreferences에서 currentIndex 불러오기
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                int currentIndex = sharedPreferences.getInt("currentIndex", 0);  // currentIndex 불러오기

                SharedPreferences.Editor editor = sharedPreferences.edit();

                // 이미지가 4개 이상 저장될 때 배열을 한 칸씩 밀기
                if (currentIndex >= 4) {
                    // 1번 배열을 삭제하고 나머지를 한 칸씩 위로 밀기
                    for (int i = 0; i < 3; i++) {
                        String nextImageUri = sharedPreferences.getString("imageUri_" + (i + 1), null);
                        String nextTimestamp = sharedPreferences.getString("timestamp_" + (i + 1), null);
                        editor.putString("imageUri_" + i, nextImageUri);  // 한 칸씩 위로 밀기
                        editor.putString("timestamp_" + i, nextTimestamp);  // 타임스탬프도 함께 이동
                    }

                    // 4번째 칸에 새로운 이미지와 타임스탬프 저장
                    String bestPrediction = predictions[0].className;  // 가장 높은 유사도를 가진 병명
                    String timestampWithPrediction = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()) + " (" + bestPrediction + ")";
                    editor.putString("imageUri_3", selectedImageUri.toString());
                    editor.putString("timestamp_3", timestampWithPrediction);

                } else {
                    // currentIndex가 4 미만일 때는 현재 인덱스에 이미지 저장
                    String bestPrediction = predictions[0].className;  // 가장 높은 유사도를 가진 병명
                    String timestampWithPrediction = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()) + " (" + bestPrediction + ")";
                    editor.putString("imageUri_" + currentIndex, selectedImageUri.toString());  // 이미지 URI 저장
                    editor.putString("timestamp_" + currentIndex, timestampWithPrediction);  // 타임스탬프와 병명 저장
                    currentIndex++;  // currentIndex 증가
                }

                // 업데이트된 currentIndex 저장
                editor.putInt("currentIndex", currentIndex > 4 ? 4 : currentIndex);  // currentIndex가 4 이상이면 4로 고정
                editor.apply();

                // MainActivity로 돌아가기 위한 새로운 Intent 생성
                Intent mainIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        LinearLayout checkResultButton = findViewById(R.id.mri_check);
        checkResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (predictions != null && predictions.length > 0) {
                    String bestPrediction = predictions[0].className;
                    float bestSimilarity = predictions[0].probability * 100; // 확률을 백분율로 변환

                    Intent intent;

                    // 병명에 따라 다른 레이아웃으로 이동하는 로직
                    switch (bestPrediction) {
                        case "무기폐 (Atelectasis)":
                            intent = new Intent(ResultActivity.this, InfoActivity1.class);
                            break;
                        case "심장비대 (Cardiomegaly)":
                            intent = new Intent(ResultActivity.this, InfoActivity2.class);
                            break;
                        case "삼출 (Effusion)":
                            intent = new Intent(ResultActivity.this, InfoActivity3.class);
                            break;
                        case "침윤 (Infiltration)":
                            intent = new Intent(ResultActivity.this, InfoActivity4.class);
                            break;
                        case "종괴 (Mass)":
                            intent = new Intent(ResultActivity.this, InfoActivity5.class);
                            break;
                        case "결절 (Nodule)":
                            intent = new Intent(ResultActivity.this, InfoActivity3.class);
                            break;
                        case "폐렴 (Pneumonia)":
                            intent = new Intent(ResultActivity.this, InfoActivity1.class);
                            break;
                        case "기흉 (Pneumothorax)":
                            intent = new Intent(ResultActivity.this, InfoActivity8.class);
                            break;
                        case "폐경화 (Consolidation)":
                            intent = new Intent(ResultActivity.this, InfoActivity9.class);
                            break;
                        case "부종 (Edema)":
                            intent = new Intent(ResultActivity.this, InfoActivity10.class);
                            break;
                        case "폐기종 (Emphysema)":
                            intent = new Intent(ResultActivity.this, InfoActivity11.class);
                            break;
                        case "섬유화 (Fibrosis)":
                            intent = new Intent(ResultActivity.this, InfoActivity12.class);
                            break;
                        case "흉막 비후 (Pleural Thickening)":
                            intent = new Intent(ResultActivity.this, InfoActivity13.class);
                            break;
                        case "탈장 (Hernia)":
                            intent = new Intent(ResultActivity.this, InfoActivity14.class);
                            break;
                        default:
                            intent = new Intent(ResultActivity.this, MainActivity.class);
                            break;
                    }

                    intent.putExtra("predictionResult", bestPrediction); // 예측 결과 전달
                    startActivity(intent); // 상세정보 액티비티로 이동
                } else {
                    Toast.makeText(ResultActivity.this, "이미지를 먼저 분석하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
        });
    }

    private void analyzeImage(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap enhancedBitmap = enhanceImageQuality(bitmap); // 이미지 품질 개선
            String result = classifyImage(enhancedBitmap); // 이미지 분석
            resultTextView.setText(result); // 분석 결과를 화면에 표시
        } else {
            resultTextView.setText("이미지가 없습니다."); // 예외 처리
        }
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

        // 예측 결과를 정렬
        predictions = new Prediction[15];
        for (int i = 0; i < output[0].length; i++) {
            predictions[i] = new Prediction(classNames[i], output[0][i]);
        }

        // 확률을 기준으로 내림차순 정렬
        Arrays.sort(predictions, new Comparator<Prediction>() {
            @Override
            public int compare(Prediction p1, Prediction p2) {
                return Float.compare(p2.probability, p1.probability);
            }
        });

        // 상위 5개 예측 결과와 설명을 반환
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(predictions[i].className).append(": ")
                    .append(String.format("%.2f", predictions[i].probability * 100)).append("%\n")
                    .append(diseaseDescriptions[i]).append("\n\n");
        }

        return result.toString();
    }


    // 권한 요청
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }


    // 갤러리 열기 메서드
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    // 이미지 업로드 후 분석 기능 유지, MainActivity로 정보만 전송
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();  // 선택한 이미지의 URI를 저장

            if (selectedImageUri != null) {
                try {
                    // 선택한 이미지를 비트맵으로 변환
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                    // 이미지를 축소하여 메모리 문제 방지
                    Bitmap enhancedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

                    // 분석 결과 생성 (TensorFlow Lite 모델을 사용해 분석)
                    String predictionResult = classifyImage(enhancedBitmap);

                    // 분석 결과를 리설트 액티비티 화면에 표시
                    resultTextView.setText(predictionResult);
                    Glide.with(this).load(selectedImageUri).into(resultImageView);

                    // 타임스탬프 추가
                    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ResultActivity", "이미지 로딩 중 오류 발생", e);
                    Toast.makeText(this, "이미지를 처리하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Bitmap enhanceImageQuality(Bitmap bitmap) {
        // 이미지를 224x224로 크기 조정
        return Bitmap.createScaledBitmap(bitmap, 224, 224, true);
    }


    private void initializeClassNames() {
        classNames[0] = "무기폐 (Atelectasis)";
        classNames[1] = "심장비대 (Cardiomegaly)";
        classNames[2] = "삼출 (Effusion)";
        classNames[3] = "침윤 (Infiltration)";
        classNames[4] = "종괴 (Mass)";
        classNames[5] = "결절 (Nodule)";
        classNames[6] = "폐렴 (Pneumonia)";
        classNames[7] = "기흉 (Pneumothorax)";
        classNames[8] = "폐경화 (Consolidation)";
        classNames[9] = "부종 (Edema)";
        classNames[10] = "폐기종 (Emphysema)";
        classNames[11] = "섬유화 (Fibrosis)";
        classNames[12] = "흉막 비후 (Pleural Thickening)";
        classNames[13] = "탈장 (Hernia)";
        classNames[14] = "정상 (No Finding)";
    }

    private void initializeDiseaseDescriptions() {
        diseaseDescriptions[0] = "무기폐는 폐의 일부가 부풀지 않는 상태입니다.";
        diseaseDescriptions[1] = "심장비대는 심장이 비정상적으로 커진 상태입니다.";
        diseaseDescriptions[2] = "삼출은 체액이 폐의 주변에 축적된 상태입니다.";
        diseaseDescriptions[3] = "침윤은 폐 조직에 비정상적인 물질이 침착된 상태입니다.";
        diseaseDescriptions[4] = "종괴는 조직의 비정상적인 덩어리입니다.";
        diseaseDescriptions[5] = "결절은 작은 덩어리나 혹입니다.";
        diseaseDescriptions[6] = "폐렴은 폐의 염증을 의미합니다.";
        diseaseDescriptions[7] = "기흉은 폐의 공기가 누출되어 발생하는 상태입니다.";
        diseaseDescriptions[8] = "폐경화는 폐의 경직 상태입니다.";
        diseaseDescriptions[9] = "부종은 체액이 과도하게 축적된 상태입니다.";
        diseaseDescriptions[10] = "폐기종은 폐의 공기 주머니가 파괴되는 상태입니다.";
        diseaseDescriptions[11] = "섬유화는 폐 조직이 굳어지는 상태입니다.";
        diseaseDescriptions[12] = "흉막 비후는 흉막이 두꺼워진 상태입니다.";
        diseaseDescriptions[13] = "탈장은 장기가 정상 위치에서 벗어난 상태입니다.";
        diseaseDescriptions[14] = "정상은 어떤 이상이 없는 상태입니다.";
    }

    // TensorFlow Lite 모델 로드 메서드
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("RealMDimodel2.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 예측 클래스 정의
    private static class Prediction {
        String className;
        float probability;

        Prediction(String className, float probability) {
            this.className = className;
            this.probability = probability;
        }
    }
}
