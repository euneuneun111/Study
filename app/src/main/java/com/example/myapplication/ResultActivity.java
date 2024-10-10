package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String resultText = getIntent().getStringExtra("resultText");

        TextView resultTextView = findViewById(R.id.diseaseNameTextView);
        resultTextView.setText(resultText);
    }
}
