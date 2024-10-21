package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class DiseaselistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list);  // XML 파일 이름에 맞게 변경

        // 각 TextView에 클릭 리스너 설정
        setTextViewClickListener(R.id.Athelectasis, 0);
        setTextViewClickListener(R.id.Cardiomegaly, 1);
        setTextViewClickListener(R.id.Effusion, 2);
        setTextViewClickListener(R.id.Edema, 3);
        setTextViewClickListener(R.id.Mass, 4);
        setTextViewClickListener(R.id.nodule, 5);
        setTextViewClickListener(R.id.Pneumonia, 6);
        setTextViewClickListener(R.id.Pneumo, 7);
        setTextViewClickListener(R.id.Consolidation, 8);
        setTextViewClickListener(R.id.Infiltration, 9);
        setTextViewClickListener(R.id.Emphysema, 10);
        setTextViewClickListener(R.id.Fibrosis, 11);
        setTextViewClickListener(R.id.Pleural, 12);
        setTextViewClickListener(R.id.Hernia, 13);

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    startActivity(new Intent(DiseaselistActivity.this, DiseaselistActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(DiseaselistActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(DiseaselistActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    // TextView 클릭 리스너 설정
    private void setTextViewClickListener(int textViewId, final int classIndex) {
        TextView textView = findViewById(textViewId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (classIndex) {
                    case 0:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity1.class);
                        break;
                    case 1:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity2.class);
                        break;
                    case 2:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity3.class);
                        break;
                    case 3:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity10.class);
                        break;
                    case 4:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity5.class);
                        break;
                    case 5:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity6.class);
                        break;
                    case 6:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity7.class);
                        break;
                    case 7:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity8.class);
                        break;
                    case 8:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity9.class);
                        break;
                    case 9:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity4.class);
                        break;
                    case 10:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity11.class);
                        break;
                    case 11:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity12.class);
                        break;
                    case 12:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity13.class);
                        break;
                    case 13:
                        intent = new Intent(DiseaselistActivity.this, InfoActivity14.class);
                        break;
                }

                if (intent != null) {
                    startActivity(intent);  // InfoActivity로 이동
                }
            }
        });
    }
}
