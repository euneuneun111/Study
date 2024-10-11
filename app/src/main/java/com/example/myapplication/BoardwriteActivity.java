package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BoardwriteActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1; // 이미지 선택 요청 코드
    private ImageView selectedImageView; // 선택한 이미지를 표시할 ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        ImageView backView = findViewById(R.id.iv_arrow_left_board);
        selectedImageView = findViewById(R.id.iv_rectangle_picture); // 선택한 이미지를 표시할 ImageView

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 업로드 버튼에 클릭 리스너 추가
        findViewById(R.id.picture_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(); // 갤러리 열기
            }
        });

        // 업로드 버튼에 클릭 리스너 추가
        findViewById(R.id.picture_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(); // 갤러리 열기
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*"); // 이미지 유형 지정
        intent.setAction(Intent.ACTION_GET_CONTENT); // 내용 가져오기 액션 설정
        startActivityForResult(Intent.createChooser(intent, "사진 선택"), PICK_IMAGE); // 결과를 받을 액티비티 시작
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData(); // 이미지 URI 가져오기
            if (imageUri != null) {
                selectedImageView.setImageURI(imageUri); // 선택한 이미지 표시
            } else {
                Toast.makeText(this, "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
