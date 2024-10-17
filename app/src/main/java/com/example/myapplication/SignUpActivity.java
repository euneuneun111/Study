package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputId, inputPassword, inputNickName, medicalNumber;
    private CheckBox checkMedical;
    private Spinner regionSpinner;
    private Button signupButton;

    private static final String IP_ADDRESS = "10.206.102.62";
    private static final String TAG = "phpsignup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); ///111

        inputId = findViewById(R.id.inputId);
        inputNickName = findViewById(R.id.inputNickName);
        inputPassword = findViewById(R.id.inputPassword);
        medicalNumber = findViewById(R.id.medicalNumber);
        checkMedical = findViewById(R.id.checkMedical);
        regionSpinner = findViewById(R.id.regionSpinner);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString().trim();
                String nickname = inputNickName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String region = regionSpinner.getSelectedItem().toString().trim();
                String doctor = checkMedical.isChecked() ? "Y" : "N";

                if (id.isEmpty() || password.isEmpty() || region.equals("- 지역을 선택해주세요 -")) {
                    Toast.makeText(SignUpActivity.this, "필수 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    new SignUpTask().execute(id, nickname, password, doctor, region);
                    Toast.makeText(SignUpActivity.this, "dd.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class SignUpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String nickname = params[1];
            String password = params[2];
            String doctor = params[3];
            String region = params[4];

            try {
                String serverURL = "http://" + IP_ADDRESS + "/Sign_up.php";
                URL url = new URL(serverURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setDoOutput(true);

                String postData = getPostDataString(new HashMap<String, String>() {{
                    put("u_id", id);
                    put("u_nickname", nickname);
                    put("u_password", password);
                    put("u_doctor", doctor);
                    put("u_region", region);
                }});

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString(); // 서버 응답 반환
                } else {
                    return "서버 응답 실패: " + responseCode; // 응답 실패 시 메시지
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage(); // 예외 발생 시 메시지
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");

                if (status.equals("success")) {
                    Toast.makeText(SignUpActivity.this, "전송 성공", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "POST response - " + result);

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    String message = jsonResponse.getString("message");
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(SignUpActivity.this, "응답 처리 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        private String getPostDataString(HashMap<String, String> params) throws Exception {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (HashMap.Entry<String, String> entry : params.entrySet()) {
                if (first) first = false;
                else result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        }
    }
}