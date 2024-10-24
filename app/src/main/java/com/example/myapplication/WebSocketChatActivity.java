package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketChatActivity extends AppCompatActivity {

    private WebSocket webSocket;
    private String roomId;
    private EditText editTextMessage;
    private ChatAdapter chatAdapter;
    private List<String> chatMessages = new ArrayList<>();
    private RecyclerView recyclerViewChat;
    private String nickname;
    private String docStatus;  // 'Y' or 'N'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // 인텐트에서 roomId 받기
        Intent intent = getIntent();
        roomId = intent.getStringExtra("room_id"); // 변경된 키 이름

        // roomId 확인
        Log.d("WebSocketChatActivity", "Received roomId: " + roomId);
        // SharedPreferences에서 u_nickname과 u_doctor 값 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        nickname = sharedPreferences.getString("u_nickname", "Unknown");
        docStatus = sharedPreferences.getString("u_doctor", "Unknown");

        // RecyclerView 설정
        recyclerViewChat = findViewById(R.id.recyclerView_chat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatMessages, this);
        recyclerViewChat.setAdapter(chatAdapter);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.0.158:8080").build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                Log.d("WebSocket", "연결 성공");
                String userDisplayName = (docStatus.equals("Y")) ? "(의료인)" + nickname : nickname;
                webSocket.send("{\"roomId\": \"" + roomId + "\", \"user\": \"" + userDisplayName + "\", \"text\": \"entered\"}");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                runOnUiThread(() -> {
                    try {
                        // 여러 줄의 기록을 줄 단위로 분리
                        String[] messages = text.split("\n");
                        for (String message : messages) {
                            JSONObject jsonObject = new JSONObject(message);
                            String user = jsonObject.getString("user");

                            if (jsonObject.has("text")) {
                                // 일반 텍스트 메시지 처리
                                String messageText = jsonObject.getString("text");
                                chatMessages.add(user + ": " + messageText);
                            } else if (jsonObject.has("image")) {
                                // 이미지 메시지 처리 (필요한 경우)
                                String imageUrl = jsonObject.getString("image");
                                chatMessages.add(user + " 이미지: " + imageUrl);
                            }
                        }

                        // 채팅 기록을 모두 추가한 후 RecyclerView 업데이트
                        chatAdapter.notifyDataSetChanged();
                        scrollToBottom();

                    } catch (JSONException e) {
                        e.printStackTrace();
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
        editTextMessage = findViewById(R.id.editText_message);

        // 텍스트 메시지 전송
        sendButton.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                // u_doctor에 따라 사용자 이름 설정
                String userDisplayName = (docStatus.equals("Y")) ? "(의료인)" + nickname : nickname;

                chatMessages.add(userDisplayName + ": " + message);
                chatAdapter.notifyDataSetChanged();
                scrollToBottom();

                // WebSocket을 통해 메시지 전송
                webSocket.send("{\"roomId\": \"" + roomId + "\", \"user\": \"" + userDisplayName + "\", \"text\": \"" + message + "\"}");
                editTextMessage.setText(""); // 입력 필드 초기화
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
