package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class BoardinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_info);

        ImageView backButton = findViewById(R.id.iv_arrow_left_board);
        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String postTitle = intent.getStringExtra("post_title");
        String postImg = intent.getStringExtra("post_img");
        String postContent = intent.getStringExtra("post_content");

        TextView titleTextView = findViewById(R.id.tv_title);
        ImageView imgImageView = findViewById(R.id.iv_text_detail); // 이미지뷰
        TextView contentTextView = findViewById(R.id.tv_content_text);

        titleTextView.setText(postTitle);
        contentTextView.setText(postContent);

        Glide.with(this)
                .load(postImg)
                .into(imgImageView);
    }
}