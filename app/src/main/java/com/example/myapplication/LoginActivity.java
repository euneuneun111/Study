package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText inputId, inputPassword;
    private Button loginButton;
    private TextView signUp, findPassword;

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
                    // 로그인 로직 (예: 서버 요청)
                    // 여기에 로그인 관련 네트워크 요청 코드를 추가할 수 있습니다.
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 회원가입 텍스트 클릭 시
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                // Intent로 새로운 회원가입 액티비티를 시작할 수 있습니다.
                Toast.makeText(LoginActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        // 비밀번호 찾기 텍스트 클릭 시
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 비밀번호 찾기 화면으로 이동
                // Intent로 새로운 비밀번호 찾기 액티비티를 시작할 수 있습니다.
                Toast.makeText(LoginActivity.this, "비밀번호 찾기 화면으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
