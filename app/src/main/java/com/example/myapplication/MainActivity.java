package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 골프 마사지 버튼 클릭 리스너
        ImageButton buttonGolf = findViewById(R.id.button_golf);
        buttonGolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Golf Massage 버튼 클릭됨", Toast.LENGTH_SHORT).show();
            }
        });

        // 뇌질환 버튼 클릭 리스너
        ImageButton buttonBrain = findViewById(R.id.button_brain);
        buttonBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Brain Disease 버튼 클릭됨", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
