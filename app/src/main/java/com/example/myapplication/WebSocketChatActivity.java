package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketChatActivity extends AppCompatActivity {

    private WebSocket webSocket;
    private String roomId;
    private EditText editTextMessage;
    private ChatAdapter chatAdapter;
    private List<String> chatMessages = new ArrayList<>();
    private RecyclerView recyclerViewChat;
    private static final int PICK_IMAGE = 1;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // RecyclerView 설정
        recyclerViewChat = findViewById(R.id.recyclerView_chat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatMessages, this);
        recyclerViewChat.setAdapter(chatAdapter);

        // 인텐트에서 roomId 받기
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.0.158:8080").build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                Log.d("WebSocket", "연결 성공");
                webSocket.send("{\"roomId\": \"" + roomId + "\", \"user\": \"User1\", \"text\": \"entered\"}");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                runOnUiThread(() -> {
                    if (text.startsWith("image:")) {
                        // 이미지 메시지 처리
                        String imageUrl = text.substring(6); // "image:" 문자열 제거
                        chatMessages.add("이미지 업로드 성공: " + imageUrl); // 이미지 업로드 성공 메시지 추가
                        chatAdapter.notifyDataSetChanged();
                        scrollToBottom(); // 메시지가 추가되면 자동 스크롤
                    } else {
                        // 일반 텍스트 메시지 처리
                        chatMessages.add(text);
                        chatAdapter.notifyDataSetChanged();
                        scrollToBottom();
                    }
                });
            }


            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                runOnUiThread(() -> Toast.makeText(WebSocketChatActivity.this, "WebSocket 연결 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e("WebSocket", "연결 실패", t);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d("WebSocket", "연결 종료: " + reason);
                runOnUiThread(() -> Toast.makeText(WebSocketChatActivity.this, "채팅방 연결이 종료되었습니다.", Toast.LENGTH_SHORT).show());
            }
        });

        Button sendButton = findViewById(R.id.button_send);
        Button buttonImageUpload = findViewById(R.id.button_image_upload);
        editTextMessage = findViewById(R.id.editText_message);

        // 텍스트 메시지 전송
        sendButton.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                // 사용자가 보낸 메시지를 리스트에 추가
                chatMessages.add("User1: " + message);
                chatAdapter.notifyDataSetChanged();
                scrollToBottom();

                // WebSocket을 통해 메시지 전송
                webSocket.send("{\"roomId\": \"" + roomId + "\", \"user\": \"User1\", \"text\": \"" + message + "\"}");
                editTextMessage.setText(""); // 입력 필드 초기화
            }
        });

        // 이미지 업로드 버튼 클릭 시
        buttonImageUpload.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "이미지 선택"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            uploadImage(selectedImageUri);
        }
    }

    private void uploadImage(Uri imageUri) {
        String imagePath = BoardwriteActivity.FileUtils.getPath(this, imageUri);
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
                .addFormDataPart("image", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.158/upload_image.php") // 서버 URL 변경 필요
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(WebSocketChatActivity.this, "이미지 업로드 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String imageUrl = response.body().string();

                    // WebSocket으로 이미지 URL을 전송
                    webSocket.send("{\"roomId\": \"" + roomId + "\", \"user\": \"User1\", \"image\": \"" + imageUrl + "\"}");

                    runOnUiThread(() -> {
                        chatMessages.add("이미지 업로드: " + imageUrl); // 업로드된 이미지 URL 추가
                        chatAdapter.notifyDataSetChanged();
                        scrollToBottom();
                        Toast.makeText(WebSocketChatActivity.this, "이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(WebSocketChatActivity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // 받은 메시지를 RecyclerView에 표시하는 로직
    private void displayMessage(String message) {
        chatMessages.add(message);
        chatAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    // 새 메시지가 추가될 때 RecyclerView를 자동으로 아래로 스크롤
    private void scrollToBottom() {
        recyclerViewChat.scrollToPosition(chatMessages.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocket != null) {
            webSocket.close(1000, "Activity 종료");
        }
    }
}
