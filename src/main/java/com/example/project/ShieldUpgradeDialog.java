package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Random;

public class ShieldUpgradeDialog extends Dialog {

    // 인터페이스 정의
    public interface OnUpgradeListener {
        void onUpgradeSuccess(int newLevel);
        void onMoneyUpdated(long newMoney); // 추가: 메소드 업데이트 인터페이스
    }

    private static final long BASE_UPGRADE_COST = 150L;
    private static final long UPGRADE_COST_MULTIPLIER = 10L;
    private static final int MAX_UPGRADE_CHANCE = 100;
    private static final int MIN_UPGRADE_CHANCE = 5;
    private static final int UPGRADE_CHANCE_DECREMENT = 5;

    private OnUpgradeListener onUpgradeListener;
    private SharedPreferences sharedPreferences;

    private ImageView equipmentImageView;
    private TextView equipmentNameTextView;
    private TextView upgradeStatsChangeTextView;
    private TextView upgradeCostTextView;
    private TextView upgradeChanceTextView;
    private Button upgradeButton;
    private Button cancelButton;

    private long availableMoney; // 보유 메소드

    private int equipmentLevel;
    private long upgradeCost;
    private int defenseMultiplier;

    private int[] shieldImages = {
            R.drawable.shield1, R.drawable.shield2, R.drawable.shield3,
            R.drawable.shield4
    };

    public ShieldUpgradeDialog(@NonNull Context context, OnUpgradeListener listener) {
        super(context);
        this.onUpgradeListener = listener;
        this.sharedPreferences = context.getSharedPreferences("equipment_prefs", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_shieldupgrade);

        initializeViews();
        restoreState();
        updateUI();

        upgradeButton.setOnClickListener(v -> attemptUpgrade());
        cancelButton.setOnClickListener(v -> dismiss());
    }

    private void initializeViews() {
        equipmentImageView = findViewById(R.id.equipmentImageView);
        equipmentNameTextView = findViewById(R.id.equipmentNameTextView);
        upgradeStatsChangeTextView = findViewById(R.id.upgradeStatsChangeTextView);
        upgradeCostTextView = findViewById(R.id.upgradeCostTextView);
        upgradeChanceTextView = findViewById(R.id.upgradeChanceTextView);
        upgradeButton = findViewById(R.id.upgradeButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void restoreState() {
        // SharedPreferences에서 int와 long 값 읽기
        equipmentLevel = sharedPreferences.getInt("shieldLevel", 1);
        upgradeCost = sharedPreferences.getLong("shieldUpgradeCost", BASE_UPGRADE_COST);
        defenseMultiplier = sharedPreferences.getInt("shieldDefenseMultiplier", 1);
        availableMoney = sharedPreferences.getLong("availableMoney", 0L); // 초기 보유 메소드 값 (예: 0L)
    }

    private void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("shieldLevel", equipmentLevel);
        editor.putLong("shieldUpgradeCost", upgradeCost);
        editor.putInt("shieldDefenseMultiplier", defenseMultiplier);
        editor.putLong("availableMoney", availableMoney); // 메소드 저장
        editor.apply();
    }

    private void updateUI() {
        equipmentNameTextView.setText(getEquipmentName());
        upgradeStatsChangeTextView.setText("방어력 x" + defenseMultiplier);
        upgradeCostTextView.setText(upgradeCost + " 메소드");
        upgradeChanceTextView.setText("강화 확률: " + getUpgradeChance() + "%");

        if (equipmentLevel <= shieldImages.length) {
            equipmentImageView.setImageResource(shieldImages[equipmentLevel - 1]);
        }
    }

    private void attemptUpgrade() {
        if (equipmentLevel < shieldImages.length) {
            if (availableMoney >= upgradeCost) { // 메소드 확인
                Random random = new Random();
                int chance = random.nextInt(100) + 1;

                // 메소드 소모
                availableMoney -= upgradeCost; // 강화 비용만큼 소모

                // 강화 비용 증가
                upgradeCost *= UPGRADE_COST_MULTIPLIER;

                if (chance <= getUpgradeChance()) {
                    equipmentLevel++;
                    defenseMultiplier++;
                    Toast.makeText(getContext(), "강화 성공! 장비가 강화되었습니다.", Toast.LENGTH_SHORT).show();
                    if (onUpgradeListener != null) {
                        onUpgradeListener.onUpgradeSuccess(equipmentLevel);
                    }
                } else {
                    Toast.makeText(getContext(), "강화 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }

                // UI 업데이트 및 상태 저장
                updateUI();
                saveState();

                // 메소드 업데이트 알림
                if (onUpgradeListener != null) {
                    onUpgradeListener.onMoneyUpdated(availableMoney);
                }
            } else {
                Toast.makeText(getContext(), "메소가 부족합니다. 메소를 충전해 주세요.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "장비가 최대 레벨에 도달했습니다!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getUpgradeChance() {
        return Math.max(MIN_UPGRADE_CHANCE, MAX_UPGRADE_CHANCE - equipmentLevel * UPGRADE_CHANCE_DECREMENT);
    }

    private String getEquipmentName() {
        return "+" + equipmentLevel + " 방패";
    }
}
