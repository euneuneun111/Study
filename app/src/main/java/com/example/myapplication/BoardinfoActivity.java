package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log; // 로그를 위한 import 추가
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.UUID;

public class BoardinfoActivity extends AppCompatActivity {

    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_info);

        // 뒤로 가기 버튼 설정
        ImageView backButton = findViewById(R.id.iv_arrow_left_board);
        backButton.setOnClickListener(v -> finish());

        // 인텐트에서 전달된 데이터 받기
        Intent intent = getIntent();
        String postTitle = intent.getStringExtra("post_title");
        String postContent = intent.getStringExtra("post_content");
        String imageUrl = intent.getStringExtra("image_url"); // 이미지 URL 받기
        roomId = intent.getStringExtra("room_id"); // 게시글에서 전달된 room_id

        // roomId 로그 출력
        Log.d("BoardinfoActivity", "Received roomId: " + roomId); // roomId를 로그에 출력

        // 텍스트 및 이미지 뷰 연결
        TextView titleTextView = findViewById(R.id.tv_title);
        TextView contentTextView = findViewById(R.id.tv_content_text);
        ImageView postImageView = findViewById(R.id.post_image); // 이미지뷰 연결

        // 제목과 내용 설정
        titleTextView.setText(postTitle);
        contentTextView.setText(postContent);

        // 이미지 URL이 있을 경우 이미지를 로드
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(postImageView);  // Glide를 사용해 이미지 로드
        } else {
            postImageView.setVisibility(View.GONE);  // 이미지가 없으면 숨김
        }

        // 대화 신청 버튼 클릭 시
        Button chatRequestButton = findViewById(R.id.chat_button);
        chatRequestButton.setOnClickListener(v -> {
            // roomId가 null이면 새로 생성, 아니면 기존 roomId 사용
            if (roomId == null) {
                roomId = generateRoomId();
            }

            // 채팅방으로 이동 (roomId 전달)
            Intent chatIntent = new Intent(BoardinfoActivity.this, WebSocketChatActivity.class);
            chatIntent.putExtra("room_id", roomId); // 변경된 키 이름
            startActivity(chatIntent);
        });
    }

    // 고유한 룸 번호 생성 (UUID 사용)
    private String generateRoomId() {
        return UUID.randomUUID().toString();
    }
}
