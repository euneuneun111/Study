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
    private EditText messageInput;
    private Button sendButton;
    private String nickname; // 닉네임 변수 추가

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String CHAT_HISTORY_KEY = "chat_history"; // 채팅 내역 저장 키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        receivedMessages = findViewById(R.id.received_messages);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        // Shared Preferences에서 닉네임 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        nickname = sharedPreferences.getString("u_nickname", null); // 저장된 닉네임 가져오기

        if (nickname != null) {
            Log.d("ChatActivity", "닉네임: " + nickname);
        } else {
            Log.e("ChatActivity", "닉네임이 null입니다.");
        }

        // WebSocket 서버 URI 설정
        URI uri = URI.create("ws://192.168.0.158:8080"); // 서버 IP와 포트
        client = new ChatWebSocketClient(uri, nickname, this); // 닉네임과 ChatActivity 인스턴스 전달
        client.connect();

        // 이전 채팅 내역 로드
        loadChatHistory();

        // 전송 버튼 클릭 리스너
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                String formattedMessage = nickname + ": " + message; // 닉네임과 메시지를 함께 포맷팅
                client.send(formattedMessage); // 메시지 전송
                receivedMessages.append("나: " + message + "\n"); // 본인 메시지 추가
                messageInput.setText(""); // 입력 필드 비우기

                // 채팅 내역 저장
                saveChatHistory(formattedMessage);
            }
        });
    }

    // 채팅 내역 저장
    private void saveChatHistory(String message) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingHistory = sharedPreferences.getString(CHAT_HISTORY_KEY, "");
        String newHistory = existingHistory + message + "\n"; // 새로운 메시지를 추가
        editor.putString(CHAT_HISTORY_KEY, newHistory);
        editor.apply();
    }

    // 이전 채팅 내역 로드
    private void loadChatHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String chatHistory = sharedPreferences.getString(CHAT_HISTORY_KEY, "");
        if (!chatHistory.isEmpty()) {
            receivedMessages.setText(chatHistory); // 로드한 채팅 내역을 표시
        }
    }

    // WebSocketClient 클래스 정의
    private class ChatWebSocketClient extends WebSocketClient {
        private String nickname; // 닉네임 저장
        private ChatActivity chatActivity; // ChatActivity 인스턴스

        public ChatWebSocketClient(URI serverUri, String nickname, ChatActivity chatActivity) {
            super(serverUri);
            this.nickname = nickname;
            this.chatActivity = chatActivity;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("서버에 연결되었습니다. (" + nickname + ")\n"));
        }

        @Override
        public void onMessage(String message) {
            chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("상대: " + message + "\n")); // 상대 메시지 추가
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
