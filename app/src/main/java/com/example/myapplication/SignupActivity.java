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
    private Button checkDuplicateButton, confirmMedicalButton, signupButton;
    private Spinner regionSpinner;
    private CheckBox checkMedical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // View 연결
        inputId = findViewById(R.id.inputId);
        inputPassword = findViewById(R.id.inputPassword);
        medicalNumber = findViewById(R.id.medicalNumber);
        checkDuplicateButton = findViewById(R.id.checkDuplicateButton);
        confirmMedicalButton = findViewById(R.id.confirmMedicalButton);
        signupButton = findViewById(R.id.signupButton);
        regionSpinner = findViewById(R.id.regionSpinner);
        checkMedical = findViewById(R.id.checkMedical);

        // 중복확인 버튼 클릭 시
        checkDuplicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString().trim();

                if (id.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 여기서 중복확인 요청을 서버에 보냅니다.
                    Toast.makeText(SignUpActivity.this, "아이디 중복확인 요청", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 의료진 확인 체크박스 체크 시
        checkMedical.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                medicalNumber.setVisibility(View.VISIBLE);
                confirmMedicalButton.setVisibility(View.VISIBLE);
            } else {
                medicalNumber.setVisibility(View.GONE);
                confirmMedicalButton.setVisibility(View.GONE);
            }
        });

        // 확인 버튼 클릭 시 (의료진 고유번호)
        confirmMedicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicalNum = medicalNumber.getText().toString().trim();

                if (medicalNum.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "의료진 고유번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 고유번호 확인 로직을 추가
                    Toast.makeText(SignUpActivity.this, "고유번호 확인 요청", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 회원가입 버튼 클릭 시
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String selectedRegion = regionSpinner.getSelectedItem().toString();

                if (id.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 회원가입 요청을 서버로 보냅니다.
                    Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    // 서버와 통신하여 회원가입 절차를 완료합니다.
                }
            }
        });
    }
}
