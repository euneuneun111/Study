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
        void onMoneyUpdated(long newMoney);
    }

    private static final String KEY_METHODS = "methods";

    private static final long BASE_UPGRADE_COST = 150L;
    private static final long UPGRADE_COST_MULTIPLIER = 10L;
    private static final int MAX_UPGRADE_CHANCE = 40;
    private static final int MIN_UPGRADE_CHANCE = 1;
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
    private int defenseMultiplier;

    private int[] shieldImages = {
            R.drawable.shield1, R.drawable.shield2, R.drawable.shield3,
            R.drawable.shield4
    };

    public ShieldUpgradeDialog(@NonNull Context context, OnUpgradeListener listener) {
        super(context);
        this.onUpgradeListener = listener;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_shieldupgrade);

        initializeViews();
        restoreState();
        loadData(); // 데이터 로드
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
        equipmentLevel = sharedPreferences.getInt("shieldLevel", 1);
        upgradeCost = sharedPreferences.getLong("shieldUpgradeCost", BASE_UPGRADE_COST);
        defenseMultiplier = sharedPreferences.getInt("shieldDefenseMultiplier", 1);
    }

    private void loadData() {
        availableMoney = sharedPreferences.getLong(KEY_METHODS, 1L); // 기본값으로 1000L 사용
    }

    private void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("shieldLevel", equipmentLevel);
        editor.putLong("shieldUpgradeCost", upgradeCost);
        editor.putInt("shieldDefenseMultiplier", defenseMultiplier);
        editor.putLong(KEY_METHODS, availableMoney); // 메소드 저장
        editor.apply();
    }

    private void updateUI() {
        equipmentNameTextView.setText(getEquipmentName());
        upgradeStatsChangeTextView.setText("메소 획득 x" + defenseMultiplier);
        upgradeCostTextView.setText(upgradeCost + " 메소");
        upgradeChanceTextView.setText("강화 확률: " + getUpgradeChance() + "%");

        if (equipmentLevel <= shieldImages.length) {
            equipmentImageView.setImageResource(shieldImages[equipmentLevel - 1]);
        }
    }

    private void attemptUpgrade() {
        if (equipmentLevel < shieldImages.length) {
            if (availableMoney >= upgradeCost) {
                Random random = new Random();
                int chance = random.nextInt(100) + 1;

                // 메소드 소모
                availableMoney -= upgradeCost;

                if (chance <= getUpgradeChance()) {
                    // 강화 성공
                    equipmentLevel++;
                    defenseMultiplier++;
                    Toast.makeText(getContext(), "강화 성공! 장비가 강화되었습니다.", Toast.LENGTH_SHORT).show();

                    // 성공 시 업그레이드 비용 증가
                    upgradeCost *= UPGRADE_COST_MULTIPLIER;

                    if (onUpgradeListener != null) {
                        onUpgradeListener.onUpgradeSuccess(equipmentLevel);
                    }
                } else {
                    // 강화 실패
                    Toast.makeText(getContext(), "강화 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }

                updateUI();
                saveState();

                if (onUpgradeListener != null) {
                    onUpgradeListener.onMoneyUpdated(availableMoney);
                }
            } else {
                Toast.makeText(getContext(), "메소가 부족합니다. ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "장비가 최대 레벨에 도달했습니다!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getUpgradeChance() {
        return Math.max(MIN_UPGRADE_CHANCE, MAX_UPGRADE_CHANCE - (equipmentLevel - 1) * UPGRADE_CHANCE_DECREMENT);
    }

    private String getEquipmentName() {
        return "+" + equipmentLevel + " 방패";
    }
}
