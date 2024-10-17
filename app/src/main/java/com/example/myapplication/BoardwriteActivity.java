package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BoardwriteActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final String SERVER_URL = "http://10.0.2.2/post_input.php";
    private ImageView selectedImageView;
    private EditText titleEditText;
    private EditText contentEditText;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        selectedImageView = findViewById(R.id.iv_rectangle_picture);
        titleEditText = findViewById(R.id.et_title); // 제목 입력 필드
        contentEditText = findViewById(R.id.et_content); // 내용 입력 필드

        findViewById(R.id.iv_arrow_left_board).setOnClickListener(view -> finish());
        findViewById(R.id.picture_text).setOnClickListener(view -> openGallery());
        findViewById(R.id.picture_upload).setOnClickListener(view -> openGallery());

        findViewById(R.id.tv_upload).setOnClickListener(view -> uploadPost());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "사진 선택"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                selectedImageView.setImageURI(selectedImageUri);
            } else {
                Toast.makeText(this, "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPost() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        if (selectedImageUri == null || title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하고 이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        File imageFile = new File(FileUtils.getPath(this, selectedImageUri));
        if (!imageFile.exists()) {
            Toast.makeText(this, "이미지 파일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("p_title", title)
                .addFormDataPart("p_content", content)
                .addFormDataPart("p_img", imageFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), imageFile))
                .build();

        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(BoardwriteActivity.this, "업로드 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e("UPLOAD_ERROR", e.getMessage(), e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(BoardwriteActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(BoardwriteActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
    public static class FileUtils {
        public static String getPath(Context context, Uri uri) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(columnIndex);
                cursor.close();
                return path;
            }
            return null;
        }
    }

}
