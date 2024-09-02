package com.example.project;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

public class Basic extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_FLOOR = "floor";
    private static final String KEY_HEALTH = "health";
    private static final String KEY_METHODS = "methods";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_ATTACK_POWER = "attack_power";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_ATTACK_MULTIPLIER = "attack_multiplier";

    private static final int BASE_EXP = 15; // 층수별 기본 경험치
    private static final float EXP_INCREMENT = 0.20f; // 층수에 따른 경험치 증가 비율
    private static final int INITIAL_EXP = 150; // 레벨 1에서 2로 가기 위한 기본 경험치
    private static final float LEVEL_UP_INCREMENT = 0.10f; // 레벨업마다 10%씩 증가
    private static final int LEVEL_MULTIPLIER_INTERVAL = 10; // 10레벨마다 필요 경험치량 2배 증가

    private TextView currentFloorValue;
    private TextView availableMethodsValue;
    private TextView healthBarValue;
    private TextView characterLevelValue;
    private TextView characterAttackValue;
    private ProgressBar experienceBar;
    private TextView experiencePercentage;

    private ImageView attackGif;  // GIF 이미지 뷰
    private LinearLayout secondLayout; // 두 번째 레이아웃 (배경 변경할 레이아웃)

    private int currentFloor;
    private int currentHealth;
    private long availableMethods; // 변경된 타입
    private int currentExperience;
    private int requiredExperience;
    private int attackPower;
    private int currentLevel;
    private int attackMultiplier; // 공격력 배수

    private Handler handler;
    private Runnable healthRunnable;

    private Button menuItem1;
    private Button menuItem2;
    private Button menuItem3;
    private Button menuItem4;

    private Fragment currentFragment;

    private ImageView monsterImageView; // 몬스터 ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);

        // 초기화 및 데이터 로드
        initializeViews();
        initializeData(); // 초기 데이터 처리
        loadSavedData();
        updateUI();
        setupMenuButtons();
        startHealthReduction();
    }

    private void initializeViews() {
        currentFloorValue = findViewById(R.id.currentFloorValue);
        availableMethodsValue = findViewById(R.id.availableMethodsValue);
        healthBarValue = findViewById(R.id.healthBarValue);
        characterLevelValue = findViewById(R.id.characterLevelValue);
        characterAttackValue = findViewById(R.id.characterAttackValue);
        experienceBar = findViewById(R.id.experienceBar);
        experiencePercentage = findViewById(R.id.experiencePercentage);

        menuItem1 = findViewById(R.id.menuItem1);
        menuItem2 = findViewById(R.id.menuItem2);
        menuItem3 = findViewById(R.id.menuItem3);
        menuItem4 = findViewById(R.id.menuItem4);

        attackGif = findViewById(R.id.attackGif);  // 레이아웃에 추가된 ImageView 연결
        attackGif.setVisibility(View.INVISIBLE);  // 초기에는 GIF 숨김

        secondLayout = findViewById(R.id.secondLayout); // 두 번째 레이아웃

        monsterImageView = findViewById(R.id.monsterImageView);

        // 초기 몬스터를 monsterzero로 설정
        monsterImageView.setImageResource(R.drawable.monsterzero);
        monsterImageView.setVisibility(View.VISIBLE); // 몬스터를 보이도록 설정

        // 초기 배경을 backgroundzero로 설정
        secondLayout.setBackgroundResource(R.drawable.backgroundzero);
    }

    private void initializeData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            resetPreferences(); // 데이터 삭제
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstRun", false); // 이후에는 데이터 삭제하지 않음
            editor.apply();
        }
    }

    private void resetPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear(); // 모든 데이터를 삭제합니다.
        editor.apply();
    }

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentFloor = prefs.getInt(KEY_FLOOR, 1);
        currentHealth = prefs.getInt(KEY_HEALTH, getMaxHealth());
        availableMethods = prefs.getLong(KEY_METHODS, 0L); // 로드 시 long으로 가져오기
        currentExperience = prefs.getInt(KEY_EXPERIENCE, 0);
        attackPower = prefs.getInt(KEY_ATTACK_POWER, 5);
        currentLevel = prefs.getInt(KEY_LEVEL, 1);
        attackMultiplier = prefs.getInt(KEY_ATTACK_MULTIPLIER, 1); // 배수 로드
    }

    private void updateUI() {
        currentFloorValue.setText(String.valueOf(currentFloor));
        availableMethodsValue.setText(String.valueOf(availableMethods));
        healthBarValue.setText(String.valueOf(currentHealth));
        characterLevelValue.setText(String.valueOf(currentLevel));
        characterAttackValue.setText(String.valueOf(attackPower * attackMultiplier)); // 공격력에 배수 적용

        requiredExperience = getRequiredExperience();
        experienceBar.setProgress(currentExperience);
        experienceBar.setMax(requiredExperience);

        float percentage = (currentExperience / (float) requiredExperience) * 100;
        experiencePercentage.setText(String.format("%.2f%%", percentage));
    }

    private void setupMenuButtons() {
        menuItem1.setOnClickListener(v -> handleMenuItemClick(new EnhancementFragment()));
        menuItem2.setOnClickListener(v -> handleMenuItemClick(new EquipmentFragment()));
        menuItem3.setOnClickListener(v -> handleMenuItemClick(new AchievementsFragment()));
        menuItem4.setOnClickListener(v -> handleMenuItemClick(new StoreFragment()));
    }

    private void handleMenuItemClick(Fragment newFragment) {
        if (currentFragment != null && currentFragment.getClass().equals(newFragment.getClass())) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            replaceFragment(newFragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.thirdLayout, fragment);
        fragmentTransaction.addToBackStack(null); // 현재 프래그먼트를 스택에 추가
        fragmentTransaction.commit();

        currentFragment = fragment;
    }

    private void startHealthReduction() {
        handler = new Handler();
        healthRunnable = new Runnable() {
            @Override
            public void run() {
                currentHealth -= attackPower * attackMultiplier; // 공격력에 배수 적용

                if (currentHealth <= 0) {
                    currentFloor++;
                    availableMethods += getMaxHealth();
                    currentHealth = getMaxHealth();

                    updateMonster(); // 몬스터 업데이트
                    updateBackground(); // 배경 업데이트

                    currentExperience += getExperienceForCurrentFloor(); // 경험치 추가
                    gainExperience(); // 레벨업 처리

                    saveData(); // 데이터 저장
                }

                updateUI(); // UI 업데이트
                playAttackGif(); // GIF 재생

                handler.postDelayed(this, 1000); // 1초마다 체력 감소
            }
        };
        handler.post(healthRunnable);
    }

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_FLOOR, currentFloor);
        editor.putInt(KEY_HEALTH, currentHealth);
        editor.putLong(KEY_METHODS, availableMethods); // 저장 시 long으로 저장
        editor.putInt(KEY_EXPERIENCE, currentExperience);
        editor.putInt(KEY_ATTACK_POWER, attackPower);
        editor.putInt(KEY_LEVEL, currentLevel);
        editor.putInt(KEY_ATTACK_MULTIPLIER, attackMultiplier); // 배수 저장
        editor.apply();
    }

    private int getMaxHealth() {
        int baseHealth = 30; // 초기 체력

        // 각 층마다 50%씩 증가
        double healthMultiplier = 1 + 0.50 * (currentFloor - 1);
        baseHealth *= healthMultiplier;

        // 5층마다 2배 증가
        int multiplier5 = (currentFloor - 1) / 5;
        baseHealth *= Math.pow(2, multiplier5);

        // 10층마다 5배 증가
        int multiplier10 = (currentFloor - 1) / 10;
        baseHealth *= Math.pow(5, multiplier10);

        return baseHealth;
    }

    private int getExperienceForCurrentFloor() {
        return (int) (BASE_EXP * Math.pow(1 + EXP_INCREMENT, currentFloor - 1));
    }

    private int getRequiredExperience() {
        int baseExperience = INITIAL_EXP;
        float multiplier = 1 + LEVEL_UP_INCREMENT * (currentLevel - 1);
        int multiplierLevel = (currentLevel - 1) / LEVEL_MULTIPLIER_INTERVAL;
        multiplier *= Math.pow(2, multiplierLevel);
        return (int) (baseExperience * multiplier);
    }

    private void gainExperience() {
        requiredExperience = getRequiredExperience();

        while (currentExperience >= requiredExperience) {
            currentExperience -= requiredExperience;
            currentLevel++;
            attackPower += 5; // 레벨업 시 공격력 증가
            requiredExperience = getRequiredExperience(); // 새로운 레벨에 필요한 경험치 업데이트
        }
        updateUI(); // UI 업데이트 호출
        saveData(); // 변경된 데이터 저장
    }

    @Override
    protected void onPause() {
        super.onPause();
        // `Dialog`로 인해 액티비티가 멈추는 경우를 방지
        if (!isFinishing() && !isChangingConfigurations()) {
            // 필요한 작업이 있다면 여기서 처리
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // `Dialog`가 닫힌 후 다시 백그라운드 작업이 실행되도록 조정
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(healthRunnable); // 액티비티 종료 시 핸들러 콜백 제거
    }

    private void playAttackGif() {
        attackGif.setVisibility(View.VISIBLE);

        // Glide를 사용하여 GIF 로드 및 재생
        Glide.with(this)
                .asGif()
                .load(R.drawable.attack1)
                .into(attackGif);

        // 일정 시간 후 GIF 숨기기
        attackGif.postDelayed(() -> attackGif.setVisibility(View.INVISIBLE), 2000); // 2초 후 숨기기
    }

    private void updateBackground() {
        if (currentFloor % 5 == 1) {
            int randomBackground = getRandomBackground(); // 랜덤 배경 이미지 선택
            secondLayout.setBackgroundResource(randomBackground);
        }
    }

    private int getRandomBackground() {
        int[] backgrounds = { R.drawable.background1, R.drawable.background2, R.drawable.background3, R.drawable.background4 };
        int randomIndex = new Random().nextInt(backgrounds.length);
        return backgrounds[randomIndex];
    }

    private void updateMonster() {
        if (currentFloor == 1) {
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.monsterzero)
                    .into(monsterImageView);
        } else {
            int monsterDrawable = getRandomMonster();
            Glide.with(this)
                    .asGif()
                    .load(monsterDrawable)
                    .into(monsterImageView);
        }
        monsterImageView.setVisibility(View.VISIBLE);
    }

    private int getRandomMonster() {
        int[] monsters = {
                R.drawable.monster1,
                R.drawable.monster3,
                R.drawable.monster4,
                R.drawable.monster5
        };
        int randomIndex = new Random().nextInt(monsters.length);
        return monsters[randomIndex];
    }

    public void updatePlayerMoney(long newMoney) {
        availableMethods = newMoney;
        availableMethodsValue.setText(String.valueOf(availableMethods));
        saveData(); // 변경된 데이터 저장
    }

    public void updateAttackPower(int newAttackPower) {
        attackPower = newAttackPower;
        characterAttackValue.setText(String.valueOf(attackPower * attackMultiplier)); // 공격력에 배수 적용
        saveData(); // 변경된 데이터 저장
    }

    public void updateLevel(int newLevel) {
        currentLevel = newLevel;
        characterLevelValue.setText(String.valueOf(currentLevel));
        saveData(); // 변경된 데이터 저장
    }

    public void updateAttackMultiplier(int newMultiplier) {
        attackMultiplier = newMultiplier;
        updateUI(); // UI 업데이트
        saveData(); // 변경된 데이터 저장
    }
}

