package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile_nickname, profile_id, profile_doc, profile_re;
    private static final String IP_ADDRESS = "192.168.0.158";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_profile);

        // View 초기화
        ImageView Backview = findViewById(R.id.iv_arrow_left_board);
        profile_nickname = findViewById(R.id.profile_nickname);
        profile_id = findViewById(R.id.profile_id);
        profile_doc = findViewById(R.id.profile_doc);
        profile_re = findViewById(R.id.profile_re);

        String userId = getIntent().getStringExtra("u_id");

        if (userId != null) {
            new FetchUserDataTask().execute(userId);
        } else {
            Toast.makeText(this, "사용자 ID가 제공되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }

        Backview.setOnClickListener(v -> finish());

        TextView Morehospital = findViewById(R.id.hospital_more);
        Morehospital.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MoreHospital.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_board) {
                    startActivity(new Intent(ProfileActivity.this, BoardActivity.class));
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    return true;
                }
                return false;
            }
        });
    }

    private class FetchUserDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String userId = params[0];
            String serverURL = "http://" + IP_ADDRESS + "/getUserData.php?u_id=" + userId;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                Log.d("ProfileActivity", "서버 응답: " + result);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("user")) {
                    JSONObject userObject = jsonObject.getJSONObject("user");
                    String nickname = userObject.optString("u_nickname", "Unknown");
                    String id = userObject.optString("u_id", "Unknown");
                    String doc = userObject.optString("u_doctor", "Unknown");
                    String region = userObject.optString("u_region", "Unknown");

                    profile_nickname.setText("NickName: " + nickname);
                    profile_id.setText("Id: " + id);
                    profile_doc.setText("Doctor: " + doc);
                    profile_re.setText("Region: " + region);
                } else {
                    Toast.makeText(ProfileActivity.this, "사용자 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "데이터 처리 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
