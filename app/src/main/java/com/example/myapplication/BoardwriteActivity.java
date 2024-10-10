package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardwriteActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        ImageView Backview = findViewById(R.id.iv_arrow_left_board);

        Backview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 실행할 작업을 여기에 작성

                // 예: 뒤로 가기 동작 구현
                finish();  // 현재 액티비티를 종료하고 이전 액티비티로 돌아갑니다.
            }
        });

    }
}
