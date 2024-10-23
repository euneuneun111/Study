package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class BoardinfoActivity extends AppCompatActivity {

    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_info);

        ImageView backButton = findViewById(R.id.iv_arrow_left_board);
        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String postTitle = intent.getStringExtra("post_title");
        String postContent = intent.getStringExtra("post_content");
        roomId = intent.getStringExtra("roomId"); // 게시글에서 전달된 roomId

        TextView titleTextView = findViewById(R.id.tv_title);
        TextView contentTextView = findViewById(R.id.tv_content_text);

        titleTextView.setText(postTitle);
        contentTextView.setText(postContent);

        // 대화 신청 버튼 클릭 시
        Button chatRequestButton = findViewById(R.id.chat_button);
        chatRequestButton.setOnClickListener(v -> {
            // roomId가 인텐트로 전달되었으면 그대로 사용, 없으면 새로 생성
            if (roomId == null) {
                roomId = generateRoomId();
            }

            // 채팅방으로 이동 (roomId 전달)
            Intent chatIntent = new Intent(BoardinfoActivity.this, WebSocketChatActivity.class);
            chatIntent.putExtra("roomId", roomId);
            startActivity(chatIntent);
        });
    }

    // 고유한 룸 번호 생성 (UUID 사용)
    private String generateRoomId() {
        return UUID.randomUUID().toString();
    }
}
