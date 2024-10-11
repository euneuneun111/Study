package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DiseaselistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list);


        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    startActivity(new Intent(DiseaselistActivity.this, BoardActivity.class));
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



}
