package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EquipmentFragment extends Fragment {

    private ImageView weaponImageView;
    private ImageView shieldImageView;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);

        weaponImageView = view.findViewById(R.id.weaponImageView);
        shieldImageView = view.findViewById(R.id.shieldImageView);

        sharedPreferences = getActivity().getSharedPreferences("equipment_prefs", Context.MODE_PRIVATE);

        restoreEquipmentState(); // 상태 복원

        view.findViewById(R.id.weaponLayout).setOnClickListener(v -> showWeaponUpgradeDialog());
        view.findViewById(R.id.shieldLayout).setOnClickListener(v -> showShieldUpgradeDialog());

        return view;
    }

    private void showWeaponUpgradeDialog() {
        WeaponUpgradeDialog weaponUpgradeDialog = new WeaponUpgradeDialog(getContext(), new WeaponUpgradeDialog.OnUpgradeListener() {
            @Override
            public void onUpgradeSuccess(int newLevel) {
                weaponImageView.setImageResource(getWeaponImageResource(newLevel));
                saveEquipmentState(); // 상태 저장
            }

            @Override
            public void onMoneyUpdated(long newMoney) {
                // 이 메서드가 필요하지 않다면, 빈 구현으로 두어야 합니다.
            }
        });
        weaponUpgradeDialog.show();
    }

    private void showShieldUpgradeDialog() {
        ShieldUpgradeDialog shieldUpgradeDialog = new ShieldUpgradeDialog(getContext(), new ShieldUpgradeDialog.OnUpgradeListener() {
            @Override
            public void onUpgradeSuccess(int newLevel) {
                shieldImageView.setImageResource(getShieldImageResource(newLevel));
                saveEquipmentState(); // 상태 저장
            }

            @Override
            public void onMoneyUpdated(long newMoney) {
                // 이 메서드가 필요하지 않다면, 빈 구현으로 두어야 합니다.
            }
        });
        shieldUpgradeDialog.show();
    }

    private int getWeaponImageResource(int level) {
        int[] weaponImages = {
                R.drawable.weapon1, R.drawable.weapon2, R.drawable.weapon3,
                R.drawable.weapon4, R.drawable.weapon5, R.drawable.weapon6,
                R.drawable.weapon7, R.drawable.weapon8, R.drawable.weapon9,
                R.drawable.weapon10, R.drawable.weapon11, R.drawable.weapon12
        };
        return weaponImages[Math.min(level - 1, weaponImages.length - 1)];
    }

    private int getShieldImageResource(int level) {
        int[] shieldImages = {
                R.drawable.shield1, R.drawable.shield2, R.drawable.shield3,
                R.drawable.shield4,
        };
        return shieldImages[Math.min(level - 1, shieldImages.length - 1)];
    }

    private void saveEquipmentState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 장비 상태 저장
        editor.putInt("weaponLevel", getWeaponLevel());
        editor.putInt("shieldLevel", getShieldLevel());

        editor.apply();
    }

    private void restoreEquipmentState() {
        int weaponLevel = sharedPreferences.getInt("weaponLevel", 1); // 기본값 1
        int shieldLevel = sharedPreferences.getInt("shieldLevel", 1); // 기본값 1

        // 이미지 업데이트
        weaponImageView.setImageResource(getWeaponImageResource(weaponLevel));
        shieldImageView.setImageResource(getShieldImageResource(shieldLevel));
    }

    private int getWeaponLevel() {
        return sharedPreferences.getInt("weaponLevel", 1);
    }

    private int getShieldLevel() {
        return sharedPreferences.getInt("shieldLevel", 1);
    }
}
