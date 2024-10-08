package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView resultText;
    private Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        resultText = findViewById(R.id.resultText);
        Button uploadButton = findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        try {
            // TensorFlow Lite 모델 로드
            tflite = new Interpreter(loadModelFile("your_model.tflite"));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Model loading failed", Toast.LENGTH_SHORT).show();
        }
    }

    // 이미지 업로드를 위한 Intent 시작
    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    // 이미지 선택 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            classifyImage(imageUri);
        }
    }

    // 이미지 분류 및 결과 표시
    private void classifyImage(Uri imageUri) {
        // TensorFlow Lite 모델로 이미지를 분류하는 코드
        String result = "X 질환";  // 예시 질환명
        float confidence = 95.0f;  // 예시 신뢰도

        // 결과를 TextView에 표시
        resultText.setText(result + " (신뢰도: " + confidence + "%)");
    }

    // 모델 파일을 로드하는 메서드
    private MappedByteBuffer loadModelFile(String modelPath) throws IOException
