package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

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
    private static final String SERVER_URL = "http://192.168.0.158/post_input.php";
    private ImageView selectedImageView;
    private EditText titleEditText;
    private EditText contentEditText;
    private Uri selectedImageUri;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);//111

        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);
        selectedImageView = findViewById(R.id.image_upload);

        findViewById(R.id.iv_arrow_left_board).setOnClickListener(view -> finish());
        findViewById(R.id.picture_text).setOnClickListener(view -> openGallery());
        findViewById(R.id.picture_upload).setOnClickListener(view -> openGallery());

        // 게시글 작성 버튼 클릭 시 고유한 룸 번호 생성 후 업로드
        findViewById(R.id.tv_upload).setOnClickListener(view -> {
            // 고유한 룸 번호 생성
            roomId = generateRoomId();

            // 서버로 글 정보와 함께 룸 번호 전송
            uploadPost(roomId);
        });
    }

    // 고유한 룸 번호 생성 (UUID 사용)
    private String generateRoomId() {
        return UUID.randomUUID().toString();
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
                selectedImageView.setVisibility(View.VISIBLE);
                selectedImageView.setImageURI(selectedImageUri);
            } else {
                Toast.makeText(this, "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 게시글과 룸 번호 서버로 전송
    private void uploadPost(String roomId) {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        if (selectedImageUri == null || title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하고 이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 파일 경로 얻기
        String imagePath = FileUtils.getPath(this, selectedImageUri);
        if (imagePath == null) {
            Toast.makeText(this, "이미지 경로를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            Toast.makeText(this, "이미지 파일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("p_title", title)
                .addFormDataPart("p_content", content)
                .addFormDataPart("room_id", roomId)
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
                    String responseBody = response.body().string();
                    try {
                        // JSON 파싱
                        JSONObject json = new JSONObject(responseBody);
                        if (json.getBoolean("success")) {
                            String imageUrl = json.getString("image_url");  // 서버에서 받은 이미지 URL
                            runOnUiThread(() -> {
                                Toast.makeText(BoardwriteActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BoardwriteActivity.this, BoardActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        } else {
                            // 여기에서 JSONException 예외 처리
                            String errorMessage = json.getString("message");  // 예외 발생 가능성 있음
                            runOnUiThread(() -> Toast.makeText(BoardwriteActivity.this, "업로드 실패: " + errorMessage, Toast.LENGTH_SHORT).show());
                        }
                    } catch (JSONException e) {
                        // JSONException 예외 처리
                        runOnUiThread(() -> Toast.makeText(BoardwriteActivity.this, "JSON 파싱 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        Log.e("JSON_ERROR", e.getMessage(), e);
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(BoardwriteActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show());
                }
            }

        });
    }

    public static class FileUtils {
        public static String getPath(Context context, Uri uri) {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                if (inputStream == null) return null;

                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "uploaded_image.jpg");
                OutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
                return file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
