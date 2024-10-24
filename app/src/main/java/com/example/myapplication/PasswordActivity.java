package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PasswordActivity extends AppCompatActivity {

    private EditText inputId, inputNickname;
    private Button passwordButton;

    private static final String IP_ADDRESS = "192.168.0.158";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        // View 연결
        inputNickname = findViewById(R.id.inputNickName);
        inputId = findViewById(R.id.inputId);
        passwordButton = findViewById(R.id.password_Button);

        // 패스워드 찾기 버튼 클릭 시
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString().trim();
                String nickname = inputNickname.getText().toString().trim();

                // ID 또는 닉네임이 비어 있을 경우
                if (id.isEmpty() || nickname.isEmpty()) {
                    Toast.makeText(PasswordActivity.this, "닉네임과 아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 서버에 패스워드 요청
                    new PasswordTask().execute(id, nickname);
                }
            }
        });

        Button loginbutton = findViewById(R.id.login_Button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 화면으로 이동
                startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
            }
        });
    }



    // 서버와 비동기 통신을 위한 PasswordTask 클래스
    private class PasswordTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String nickname = params[1];
            String serverURL = "http://" + IP_ADDRESS + "/password_find.php"; // 서버의 패스워드 찾기 URL

            try {
                // 서버에 요청할 데이터 설정 (ID와 닉네임)
                String postData = "u_id=" + URLEncoder.encode(id, "UTF-8") +
                        "&u_nickname=" + URLEncoder.encode(nickname, "UTF-8");

                // URL 연결 설정
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                // 요청 데이터 전송
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();

                // 응답 받기
                int responseStatusCode = httpURLConnection.getResponseCode();
                BufferedReader bufferedReader;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                }

                // 응답 데이터를 읽어오기
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim(); // 결과 반환

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("PasswordActivity", "Server response: " + result); // 서버 응답 로깅

            if (result != null && !result.isEmpty()) {
                try {
                    // JSON 응답 파싱
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    if (jsonArray.length() > 0) {
                        JSONObject obj = jsonArray.getJSONObject(0);
                        // 메시지 확인
                        if (obj.has("message")) {
                            String message = obj.getString("message");
                            Toast.makeText(PasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                        // 패스워드가 있는 경우 확인
                        if (obj.has("password")) {
                            String password = obj.getString("password");

                            // UI 업데이트
                            Button loginButton = findViewById(R.id.login_Button);
                            loginButton.setVisibility(View.VISIBLE);

                            TextView passwordtitle = findViewById(R.id.password_title);
                            TextView passwordtext = findViewById(R.id.password_text);

                            passwordtitle.setVisibility(View.VISIBLE);
                            passwordtext.setVisibility(View.VISIBLE);

                            passwordtext.setText(password);
                        }
                    } else {
                        // 데이터가 없을 때
                        Toast.makeText(PasswordActivity.this, "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PasswordActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 서버 응답이 비어 있을 때
                Toast.makeText(PasswordActivity.this, "서버 응답이 비어 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
