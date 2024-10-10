package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class BoardinfoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_info);

        ImageView backButton = findViewById(R.id.iv_arrow_left_board); // < 버튼의 id를 backButton으로 설정
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 새로운 Activity로 이동
                Intent intent = new Intent(BoardinfoActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
