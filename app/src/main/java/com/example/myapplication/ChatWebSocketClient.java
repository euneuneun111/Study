package com.example.myapplication;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ChatWebSocketClient extends WebSocketClient {
    private String nickname;
    private ChatActivity chatActivity; // ChatActivity 인스턴스를 추가

    public ChatWebSocketClient(URI serverUri, String nickname, ChatActivity chatActivity) {
        super(serverUri);
        this.nickname = nickname; // 닉네임 저장
        this.chatActivity = chatActivity; // ChatActivity 인스턴스 저장
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("서버에 연결되었습니다. (" + nickname + ")\n"));
    }

    @Override
    public void onMessage(String message) {
        chatActivity.runOnUiThread(() -> chatActivity.receivedMessages.append("수신된 메시지: " + message + "\n"));
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
