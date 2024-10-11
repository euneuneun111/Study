package com.example.myapplication;

import android.content.Intent;
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
import java.util.Arrays;
import java.util.Comparator;

public class ResultActivity extends AppCompatActivity {

    private ImageView resultImageView;
    private TextView resultTextView;
    private Interpreter tflite;
    private String[] classNames = new String[15]; // 클래스 이름 배열
    private String[] diseaseDescriptions = new String[15]; // 질병 설명 배열

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        LinearLayout infoButton = findViewById(R.id.mri_check);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 실행할 작업을 여기에 작성

                Intent intent = new Intent(ResultActivity.this,InfoActivity.class);
                startActivity(intent);
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


        resultImageView = findViewById(R.id.mriImageView);
        resultTextView = findViewById(R.id.result_text_view);

        // 클래스 이름과 설명 먼저 초기화
        initializeClassNames(); // 클래스 이름 초기화
        initializeDiseaseDescriptions(); // 질병 설명 초기화

        // 모델 로드 및 초기화
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ClassifyActivity", "TensorFlow Lite 모델 로드 중 오류 발생", e);
        }

        // Intent로부터 전달받은 이미지 URI
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageUriString);



        // 이미지 표시
        Glide.with(this)
                .load(imageUri)
                .into(resultImageView);

        try {
            // 이미지 분석 및 예측
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
            String predictionResult = classifyImage(resizedBitmap);

            // 예측 결과 표시
            resultTextView.setText(predictionResult);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ClassifyActivity", "이미지 로딩 중 오류 발생", e);
        }


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

        Log.d("ClassifyActivity", "Class names initialized: " + Arrays.toString(classNames));
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

        Log.d("ClassifyActivity", "Disease descriptions initialized: " + Arrays.toString(diseaseDescriptions));
    }

    // TensorFlow Lite 모델 로드 메서드 추가
    private MappedByteBuffer loadModelFile() throws IOException {
        // 모델을 assets 폴더에서 로드
        String modelFileName = "RealMDimodel2.tflite"; // TFLite 모델 파일 이름
        FileInputStream inputStream = new FileInputStream(getAssets().openFd(modelFileName).getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = getAssets().openFd(modelFileName).getStartOffset();
        long declaredLength = getAssets().openFd(modelFileName).getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private String classifyImage(Bitmap bitmap) {
        // TensorFlow Lite 모델을 사용한 예측 처리
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

        // 예측 결과 처리
        Prediction[] predictions = new Prediction[15];
        for (int i = 0; i < output[0].length; i++) {
            predictions[i] = new Prediction(classNames[i], output[0][i]);
        }

        Arrays.sort(predictions, new Comparator<Prediction>() {
            @Override
            public int compare(Prediction p1, Prediction p2) {
                return Float.compare(p2.probability, p1.probability);
            }
        });

        // 예측 결과 표시
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(predictions[i].className).append(": ")
                    .append(String.format("%.2f", predictions[i].probability * 100)).append("%\n");
            // 질병 설명도 추가하여 표시
            result.append(diseaseDescriptions[i]).append("\n\n");
        }

        return result.toString();
    }

    private static class Prediction {
        String className;
        float probability;

        Prediction(String className, float probability) {
            this.className = className;
            this.probability = probability;
        }
    }
}
