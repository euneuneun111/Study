package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputId, inputPassword, medicalNumber;
    private CheckBox checkMedical;
    private Spinner regionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputId = findViewById(R.id.inputId);
        inputPassword = findViewById(R.id.inputPassword);
        medicalNumber = findViewById(R.id.medicalNumber);
        checkMedical = findViewById(R.id.checkMedical);
        regionSpinner = findViewById(R.id.regionSpinner);
        Button checkDuplicateButton = findViewById(R.id.checkDuplicateButton);
        Button confirmMedicalButton = findViewById(R.id.confirmMedicalButton);
        Button signupButton = findViewById(R.id.signupButton); ///ㅋㅌㅌ

        // 중복확인 버튼 클릭 리스너
        checkDuplicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 아이디 중복 체크 로직 (예시)
                    Toast.makeText(SignUpActivity.this, "아이디 중복확인 완료", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 의료진 확인 버튼 클릭 리스너
        confirmMedicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMedical.isChecked()) {
                    String medicalNum = medicalNumber.getText().toString();
                    if (!medicalNum.isEmpty()) {
                        // 의료진 고유번호 확인 로직 (예시)
                        Toast.makeText(SignUpActivity.this, "의료진 확인 완료", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "고유번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "의료진 체크박스를 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 회원가입 완료 버튼 클릭 리스너
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString();
                String password = inputPassword.getText().toString();
                String region = regionSpinner.getSelectedItem().toString();

                if (id.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "필수 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 회원가입 처리 로직 (예시)
                    Toast.makeText(SignUpActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
