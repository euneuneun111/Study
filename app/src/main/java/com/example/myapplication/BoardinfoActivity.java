package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_info);

        ImageView backButton = findViewById(R.id.iv_arrow_left_board);
        backButton.setOnClickListener(v -> finish());

        // 이전 액티비티에서 전달된 데이터 받기
        Intent intent = getIntent();
        String postTitle = intent.getStringExtra("post_title");
        String postContent = intent.getStringExtra("post_content");


        TextView titleTextView = findViewById(R.id.tv_title);
        TextView contentTextView = findViewById(R.id.tv_content_text);

        titleTextView.setText(postTitle);
        contentTextView.setText(postContent);
    }
}

