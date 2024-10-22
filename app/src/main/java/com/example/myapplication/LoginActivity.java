package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences; // SharedPreferences 임포트 추가
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText inputId, inputPassword;
    private Button loginButton;
    private TextView signUp, findPassword;

    private static final String IP_ADDRESS = "192.168.0.158";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // View 연결
        inputId = findViewById(R.id.inputId);
        inputPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUp);
        findPassword = findViewById(R.id.findPassword);

        // 로그인 버튼 클릭 시
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // ID 또는 비밀번호가 비어 있을 경우
                if (id.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "ID와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 서버에 로그인 요청
                    new LoginTask().execute(id, password);
                }
            }
        });

        // 회원가입 텍스트 클릭 시
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                Toast.makeText(LoginActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        // 비밀번호 찾기 텍스트 클릭 시
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 비밀번호 찾기 화면으로 이동
                Toast.makeText(LoginActivity.this, "비밀번호 찾기 화면으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String password = params[1];
            String serverURL = "http://" + IP_ADDRESS + "/login.php";

            try {
                URL url = new URL(serverURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // 인코딩된 POST 데이터
                String postData = "u_id=" + URLEncoder.encode(id, "UTF-8") + "&u_password=" + URLEncoder.encode(password, "UTF-8");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(postData);
                writer.flush();
                writer.close();

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
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (!jsonObject.has("user")) {
                    Toast.makeText(LoginActivity.this, "유효하지 않은 응답 형식입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonArray.length() > 0) {
                    JSONObject userObject = jsonArray.getJSONObject(0);
                    String u_id = userObject.optString("u_id", "unknown");
                    String u_nickname = userObject.optString("u_nickname", "unknown");
                    String u_password = userObject.optString("u_password", "unknown");
                    String u_doctor = userObject.optString("u_doctor", "unknown");
                    String u_region = userObject.optString("u_region", "unknown");

                    // Shared Preferences에 로그인 정보 저장
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("u_id", u_id);
                    editor.putString("u_nickname", u_nickname);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, u_nickname + "님 로그인 되었습니다!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("u_id", u_id);
                    intent.putExtra("u_nickname", u_nickname);
                    intent.putExtra("u_password", u_password);
                    intent.putExtra("u_doctor", u_doctor);
                    intent.putExtra("u_region", u_region);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "로그인 실패: 사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "로그인 실패: 데이터 파싱 오류", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
