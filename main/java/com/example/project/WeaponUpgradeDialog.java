package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        void onMoneyUpdated(long newMoney);
    }

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_METHODS = "methods";

    private static final long BASE_UPGRADE_COST = 200L;
    private static final long UPGRADE_COST_MULTIPLIER = 15L;
    private static final int MAX_UPGRADE_CHANCE = 60;
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

    private long availableMethods;
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
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_weaponupgrade);

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
        equipmentLevel = sharedPreferences.getInt("weaponLevel", 1);
        upgradeCost = sharedPreferences.getLong("weaponUpgradeCost", BASE_UPGRADE_COST);
        attackMultiplier = sharedPreferences.getInt("weaponAttackMultiplier", 1);
    }

    private void loadData() {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        availableMethods = prefs.getLong(KEY_METHODS, 0L);
    }

    private void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("weaponLevel", equipmentLevel);
        editor.putLong("weaponUpgradeCost", upgradeCost);
        editor.putInt("weaponAttackMultiplier", attackMultiplier);
        editor.putLong(KEY_METHODS, availableMethods); // 메소 저장
        editor.apply(); // 상태 저장을 비동기로 수행
    }

    private void updateUI() {
        equipmentNameTextView.setText(getEquipmentName());
        upgradeStatsChangeTextView.setText("공격력 x" + attackMultiplier);
        upgradeCostTextView.setText(upgradeCost + " 메소");
        upgradeChanceTextView.setText("강화 확률: " + getUpgradeChance() + "%");

        if (equipmentLevel <= weaponImages.length) {
            equipmentImageView.setImageResource(weaponImages[equipmentLevel - 1]);
        }

        // 상태 저장
        saveState();
    }

    private void attemptUpgrade() {
        if (equipmentLevel < weaponImages.length) {
            if (availableMethods >= upgradeCost) {
                Random random = new Random();
                int chance = random.nextInt(100) + 1;

                // 메소 소모
                availableMethods -= upgradeCost;
                Log.d("WeaponUpgradeDialog", "소모 후 메소: " + availableMethods);

                // 업그레이드 시도
                if (chance <= getUpgradeChance()) {
                    // 강화 성공
                    equipmentLevel++;
                    attackMultiplier++;
                    Toast.makeText(getContext(), "강화 성공! 장비가 강화되었습니다.", Toast.LENGTH_SHORT).show();

                    // 성공 시 업그레이드 비용 증가
                    upgradeCost *= UPGRADE_COST_MULTIPLIER;

                    // 성공 시 메소 업데이트
                    if (getContext() instanceof Basic) {
                        Basic basicActivity = (Basic) getContext();
                        basicActivity.updatePlayerMoney(availableMethods);
                        basicActivity.updateAttackPower(attackMultiplier);
                    }
                } else {
                    // 강화 실패
                    Toast.makeText(getContext(), "강화 실패! 다음 기회를 노려보세요.", Toast.LENGTH_SHORT).show();
                }

                // 메소 업데이트
                if (getContext() instanceof Basic) {
                    Basic basicActivity = (Basic) getContext();
                    basicActivity.updatePlayerMoney(availableMethods);
                }

                Log.d("WeaponUpgradeDialog", "UI 업데이트 시 메소: " + availableMethods);

                // UI 업데이트 및 상태 저장
                updateUI();
            } else {
                Toast.makeText(getContext(), "메소드가 부족합니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "장비가 최대 레벨에 도달했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getEquipmentName() {
        return "무기 레벨 " + equipmentLevel;
    }

    private int getUpgradeChance() {
        int chance = MAX_UPGRADE_CHANCE - (equipmentLevel - 1) * UPGRADE_CHANCE_DECREMENT;
        return Math.max(chance, MIN_UPGRADE_CHANCE);
    }
}
