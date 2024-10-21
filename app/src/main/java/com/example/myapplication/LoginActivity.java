package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

                String postData = "u_id=" + id + "&u_password=" + password;
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

            if (result.contains("no id OR wrong password")) {
                Toast.makeText(LoginActivity.this, "로그인 실패: 잘못된 ID 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("user");

                    if (jsonArray.length() > 0) {
                        JSONObject userObject = jsonArray.getJSONObject(0);
                        String u_id = userObject.getString("u_id");
                        String u_nickname = userObject.getString("u_nickname");
                        String u_password = userObject.getString("u_password");
                        String u_doctor = userObject.getString("u_doctor");
                        String u_region = userObject.getString("u_region");


                        Toast.makeText(LoginActivity.this, "로그인 성공: " + u_nickname, Toast.LENGTH_SHORT).show();


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("u_id", u_id);
                                intent.putExtra("u_nickname", u_nickname);
                                intent.putExtra("u_password", u_password);
                                intent.putExtra("u_doctor", u_doctor);
                                intent.putExtra("u_region", u_region);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "로그인 실패: 데이터 파싱 오류", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
