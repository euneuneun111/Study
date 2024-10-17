package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity{

    private static final String API_URL = "http://10.0.2.2/post_output.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 리스너 추가하여 메뉴 선택 시 동작 정의
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_write) {
                    Intent intent = new Intent(BoardActivity.this,BoardwriteActivity.class);
                    startActivity(intent);
                    // 작성 아이템 클릭 시 처리
                    return true;
                } else
                return false;
            }
        });

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