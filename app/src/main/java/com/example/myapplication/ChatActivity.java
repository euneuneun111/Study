package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

public class ChatActivity extends AppCompatActivity {

    private ChatWebSocketClient client;
    public TextView receivedMessages;
    private EditText messageInput, roomIdInput;
    private Button sendButton, enterRoomButton;
    private String nickname; // 닉네임 변수 추가

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String CHAT_HISTORY_KEY = "chat_history"; // 채팅 내역 저장 키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        receivedMessages = findViewById(R.id.received_messages);
        messageInput = findViewById(R.id.message_input);
        roomIdInput = findViewById(R.id.room_id_input); // 룸 ID 입력 필드 추가
        sendButton = findViewById(R.id.send_button);
        enterRoomButton = findViewById(R.id.enter_room_button); // 룸 입장 버튼 추가

        // Shared Preferences에서 닉네임 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        nickname = sharedPreferences.getString("u_nickname", null); // 저장된 닉네임 가져오기

        if (nickname != null) {
            Log.d("ChatActivity", "닉네임: " + nickname);
        } else {
            Log.e("ChatActivity", "닉네임이 null입니다.");
        }

        // 전송 버튼 클릭 리스너
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                client.send(roomIdInput.getText().toString() + ":" + nickname + ": " + message); // 룸 ID와 메시지를 함께 전송
                receivedMessages.append("나: " + message + "\n"); // 본인 메시지 추가
                messageInput.setText(""); // 입력 필드 비우기
            }
        });

        // 룸 입장 버튼 클릭 리스너
        enterRoomButton.setOnClickListener(v -> {
            String roomId = roomIdInput.getText().toString();
            if (!roomId.isEmpty()) {
                // WebSocket 서버 URI 설정
                URI uri = URI.create("ws://192.168.0.158:8080"); // 서버 IP와 포트
                client = new ChatWebSocketClient(uri, roomId, nickname, this); // 룸 ID와 닉네임 전달
                client.connect();
            }
        });
    }

    // WebSocketClient 클래스 정의
    private class ChatWebSocketClient extends WebSocketClient {
        private String roomId; // 룸 ID 저장
        private String nickname; // 닉네임 저장
        private ChatActivity chatActivity; // ChatActivity 인스턴스

        public ChatWebSocketClient(URI serverUri, String roomId, String nickname, ChatActivity chatActivity) {
            super(serverUri);
            this.roomId = roomId;
            this.nickname = nickname;
            this.chatActivity = chatActivity;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("서버에 연결되었습니다. (" + nickname + ") 룸: " + roomId + "\n"));
        }

        @Override
        public void onMessage(String message) {
            chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append(message + "\n")); // 메시지 추가
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("연결이 종료되었습니다: " + reason + "\n"));
        }

        @Override
        public void onError(Exception ex) {
            chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("오류 발생: " + ex.getMessage() + "\n"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close(); // 액티비티 종료 시 클라이언트 연결 종료
    }
}
