package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextView를 찾아서 변수에 할당
        TextView textView = findViewById(R.id.textView);

        // AlphaAnimation 객체 생성 (0에서 1까지 투명도 변화)
        AlphaAnimation blinkAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkAnimation.setDuration(500); // 애니메이션이 한 번 재생되는 데 걸리는 시간 (밀리초)
        blinkAnimation.setStartOffset(20); // 애니메이션 시작 전 대기 시간
        blinkAnimation.setRepeatMode(Animation.REVERSE); // 애니메이션이 끝나면 반대로 실행
        blinkAnimation.setRepeatCount(Animation.INFINITE); // 애니메이션 무한 반복

        // TextView에 애니메이션 적용
        textView.startAnimation(blinkAnimation);

        // 전체 레이아웃에 터치 이벤트 리스너 추가
        findViewById(R.id.main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 터치가 감지되면 Basic 화면으로 전환
                    Intent intent = new Intent(MainActivity.this, Basic.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
