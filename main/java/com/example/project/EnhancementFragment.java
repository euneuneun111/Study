package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EnhancementFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_METHODS = "methods";
    private static final String KEY_ATTACK_POWER = "attack_power";
    private static final String KEY_ENHANCEMENT_LEVEL = "enhancement_level";

    private static final int INITIAL_COST = 10;
    private static final float INCREASE_RATE = 0.30f; // 레벨당 비용 증가율
    private static final int MULTIPLIER_INTERVAL = 5; // 5레벨마다 배수 증가

    private static final float ATTACK_INCREASE_RATE = 0.20f; // 공격력 레벨당 증가율
    private static final int ATTACK_MULTIPLIER_INTERVAL = 5; // 5레벨마다 배수 증가

    private TextView enhanceCostText1;
    private ImageButton enhanceButton1;

    private long availableMethods;
    private int attackPower;
    private int enhancementLevel; // 강화 레벨

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enhancement, container, false);

        enhanceCostText1 = view.findViewById(R.id.enhanceCostText1);
        enhanceButton1 = view.findViewById(R.id.enhanceButton1);

        // SharedPreferences에서 초기 데이터 로드
        loadData();

        // 초기 강화 비용 설정
        updateEnhanceCostText();

        enhanceButton1.setOnClickListener(v -> onEnhanceButtonClick());

        return view;
    }

    private void loadData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        availableMethods = prefs.getLong(KEY_METHODS, 0L);
        attackPower = prefs.getInt(KEY_ATTACK_POWER, 5);
        enhancementLevel = prefs.getInt(KEY_ENHANCEMENT_LEVEL, 1); // 강화 레벨 로드
    }

    private void updateEnhanceCostText() {
        int cost = calculateEnhanceCost(enhancementLevel + 1); // 다음 레벨의 비용을 계산
        enhanceCostText1.setText(String.format("강화 비용 %d", cost));
    }

    private int calculateEnhanceCost(int level) {
        int cost = INITIAL_COST;
        for (int i = 1; i < level; i++) {
            cost = (int) (cost * (1 + INCREASE_RATE));

            // 5레벨마다 배수 증가
            if (i % MULTIPLIER_INTERVAL == 0) {
                cost *= 2;
            }
        }
        return cost;
    }

    private int calculateAttackPower(int level) {
        int initialAttackPower = 5; // 초기 공격력
        int power = initialAttackPower;

        for (int i = 1; i <= level; i++) {
            power = (int) (power * (1 + ATTACK_INCREASE_RATE));

            // 5레벨마다 공격력을 2배로 증가
            if (i % ATTACK_MULTIPLIER_INTERVAL == 0) {
                power *= 2;
            }
        }
        return power;
    }

    private void onEnhanceButtonClick() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int cost = calculateEnhanceCost(enhancementLevel + 1);

        if (availableMethods >= cost) {
            availableMethods -= cost;
            enhancementLevel++; // 강화 레벨 증가
            attackPower = calculateAttackPower(enhancementLevel); // 강화 레벨에 따라 공격력 업데이트

            // 데이터 저장
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(KEY_METHODS, availableMethods);
            editor.putInt(KEY_ATTACK_POWER, attackPower);
            editor.putInt(KEY_ENHANCEMENT_LEVEL, enhancementLevel); // 강화 레벨 저장
            editor.apply();

            // UI 업데이트
            updateEnhanceCostText();

            if (getActivity() instanceof Basic) {
                Basic basicActivity = (Basic) getActivity();
                basicActivity.updatePlayerMoney(availableMethods);
                basicActivity.updateAttackPower(attackPower);
                // 캐릭터 레벨 업데이트는 여기서 하지 않음
            }
        } else {
            // 메소드 부족 메시지 처리
            Toast.makeText(getActivity(), "메소가 부족합니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
