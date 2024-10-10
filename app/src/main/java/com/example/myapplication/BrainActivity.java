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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BrainActivity extends AppCompatActivity{

    private static final int PICK_IMAGE = 1;
    private ImageView imageView;
    private TextView resultText;
    private Interpreter tflite;

    private String[] classNames = new String[15];

    private String bestPrediction;
    private float bestSimilarity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain);

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        initializeClassNames();

        // 리스너 추가하여 메뉴 선택 시 동작 정의
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    Intent intent = new Intent(BrainActivity.this, BoardActivity.class);
                    startActivity(intent);
                    // 책 아이템 클릭 시 처리
                    return true;
                } else if (id == R.id.nav_home) {
                    Intent intent = new Intent(BrainActivity.this, MainActivity.class);
                    startActivity(intent);
                    // 홈 아이템 클릭 시 처리
                    return true;
                } else if (id == R.id.nav_profile) {
                    // 프로필 아이템 클릭 시 ProfileActivity로 전환
                    Intent intent = new Intent(BrainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        LinearLayout Lung_check = findViewById(R.id.brain_check);
        LinearLayout Lung_add = findViewById(R.id.brain_add);


        // 클릭 리스너 설정
        Lung_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bestPrediction != null) {
                    Intent intent = new Intent(BrainActivity.this, ResultActivity.class);
                    intent.putExtra("resultText", "사용자님은 '" + bestPrediction + "' 증상이 의심됩니다 유사성은 " + String.format("%.2f", bestSimilarity) + "% 입니다.");
                    startActivity(intent);
                } else {
                    Toast.makeText(BrainActivity.this, "이미지를 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
                }
                // 클릭 시 실행할 동작
            }
        });

        Lung_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });


    }

    private Bitmap enhanceImageQuality(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
        return resizedBitmap;
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Bitmap enhancedBitmap = enhanceImageQuality(bitmap);
                String predictionResult = classifyImage(enhancedBitmap);
                resultText.setText(predictionResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        Log.d("ModelLoader", "Loading model from: " + getModelPath());
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd(getModelPath());
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        Log.d("ModelLoader", "FileDescriptor: " + fileDescriptor.toString());
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }

    private String classifyImage(Bitmap bitmap) {
        if (tflite == null) {
            Log.e("TFLite", "Interpreter is not initialized.");
            return "모델이 초기화되지 않았습니다.";
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true); // 224x224 크기로 조정

        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
        inputBuffer.order(java.nio.ByteOrder.nativeOrder());

        int[] intValues = new int[224 * 224];
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());

        for (int pixelValue : intValues) {
            inputBuffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f);
            inputBuffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);
            inputBuffer.putFloat((pixelValue & 0xFF) / 255.0f);
        }

        float[][] output = new float[1][15];

        tflite.run(inputBuffer, output);

        StringBuilder result = new StringBuilder();
        bestPrediction = null;
        bestSimilarity = 0.0f;

        for (int i = 0; i < output[0].length; i++) {
            if (classNames[i] != null) {
                float similarityPercentage = output[0][i] * 100;
                result.append(classNames[i]).append(": ").append(String.format("%.2f", similarityPercentage)).append("%\n");

                if (similarityPercentage > bestSimilarity) {
                    bestSimilarity = similarityPercentage;
                    bestPrediction = classNames[i];
                }
            }
        }
        return result.toString();
    }

    private String getModelPath() {
        return "RealMDimodel2.tflite";
    }
}
