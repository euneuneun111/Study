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

public class WeaponUpgradeDialog extends Dialog {

    // 인터페이스 정의
    public interface OnUpgradeListener {
        void onUpgradeSuccess(int newLevel);
        void onMoneyUpdated(long newMoney); // 추가: 메소드 업데이트 인터페이스
    }

    private static final long BASE_UPGRADE_COST = 200L;
    private static final long UPGRADE_COST_MULTIPLIER = 15L;
    private static final int MAX_UPGRADE_CHANCE = 110;
    private static final int MIN_UPGRADE_CHANCE = 10;
    private static final int UPGRADE_CHANCE_DECREMENT = 10;

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
    private int attackMultiplier;

    private int[] weaponImages = {
            R.drawable.weapon1, R.drawable.weapon2, R.drawable.weapon3,
            R.drawable.weapon4, R.drawable.weapon5, R.drawable.weapon6,
            R.drawable.weapon7, R.drawable.weapon8, R.drawable.weapon9,
            R.drawable.weapon10, R.drawable.weapon11, R.drawable.weapon12
    };

    public WeaponUpgradeDialog(@NonNull Context context, OnUpgradeListener listener) {
        super(context);
        this.onUpgradeListener = listener;
        this.sharedPreferences = context.getSharedPreferences("equipment_prefs", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_weaponupgrade);

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

    private void loadData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        availableMethods = prefs.getLong(KEY_METHODS, 0L);
    }

    private void restoreState() {
        // 데이터가 int로 저장된 경우 기본값을 int로 읽기
        equipmentLevel = sharedPreferences.getInt("weaponLevel", 1);

        // 기본값이 long으로 저장되어야 하므로, 데이터가 int로 저장된 경우를 처리하는 방법
        upgradeCost = sharedPreferences.contains("weaponUpgradeCost")
                ? sharedPreferences.getLong("weaponUpgradeCost", BASE_UPGRADE_COST)
                : BASE_UPGRADE_COST;

        attackMultiplier = sharedPreferences.getInt("weaponAttackMultiplier", 1);
        availableMoney = sharedPreferences.getLong("availableMoney", 0L); // 초기 보유 메소드 값 (예: 1000L)
    }

    private void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("weaponLevel", equipmentLevel);
        editor.putLong("weaponUpgradeCost", upgradeCost);
        editor.putInt("weaponAttackMultiplier", attackMultiplier);
        editor.putLong("availableMoney", availableMoney); // 메소드 저장
        editor.apply();
    }

    private void updateUI() {
        equipmentNameTextView.setText(getEquipmentName());
        upgradeStatsChangeTextView.setText("공격력 x" + attackMultiplier);
        upgradeCostTextView.setText(upgradeCost + " 메소드");
        upgradeChanceTextView.setText("강화 확률: " + getUpgradeChance() + "%");

        if (equipmentLevel <= weaponImages.length) {
            equipmentImageView.setImageResource(weaponImages[equipmentLevel - 1]);
        }
    }

    private void attemptUpgrade() {
        if (equipmentLevel < weaponImages.length) {
            if (availableMoney >= upgradeCost) { // 메소드 확인
                Random random = new Random();
                int chance = random.nextInt(100) + 1;

                // 메소드 소모
                availableMoney -= upgradeCost; // 강화 비용만큼 소모

                // 강화 비용 증가
                upgradeCost *= UPGRADE_COST_MULTIPLIER;

                if (chance <= getUpgradeChance()) {
                    equipmentLevel++;
                    attackMultiplier++;
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
        return "+" + equipmentLevel + " 무기";
    }
}
