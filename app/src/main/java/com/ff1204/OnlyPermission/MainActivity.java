package com.ff1204.OnlyPermission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();
    final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };

    private final int requestCode_systemPermission = 12041;
    private final int requestCode_powerManagerPermission = 12042;
    private final int requestCode_usageStatsPermission = 12043;
    private final int requestCode_canDrawOverlaysPermission = 12044;

    private Button systemPermission_btn;
    private Button powerManagerPermission_btn;
    private Button usageStatsPermission_btn;
    private Button canDrawOverlaysPermission_btn;

    OnlyPermission mOnlyPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOnlyPermission = new OnlyPermission(this, getBaseContext());
        // getBaseContext() -> https://medium.com/@marudxlab/getbasecontext-%EC%99%80-getapplicationcontext-14ff3e0755b3

        // System 권한
        systemPermission_btn = findViewById(R.id.systemPermission_btn);
        systemPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systemPermission_btn.setEnabled(false);
                if (mOnlyPermission.checkSystemPermission(PERMISSIONS, false, requestCode_systemPermission)) {
                    systemPermission_btn.setEnabled(false);
                    systemPermission_btn.setText("권한설정 완료");
                } else {
                    systemPermission_btn.setEnabled(true);

                }
            }
        });

        // 배터리 사용량 최적화 권한
        powerManagerPermission_btn = findViewById(R.id.powerManagerPermission_btn);
        powerManagerPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnlyPermission.checkPowerManagerPermission(false, requestCode_powerManagerPermission)) {
                    powerManagerPermission_btn.setEnabled(false);
                    powerManagerPermission_btn.setText("권한설정 완료");
                } else {
                    powerManagerPermission_btn.setEnabled(true);
                }
            }
        });

        // 사용자 접근 허용
        usageStatsPermission_btn = findViewById(R.id.usageStatsPermission_btn);
        usageStatsPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnlyPermission.checkUsageStatsPermission(false, requestCode_usageStatsPermission)) {
                    usageStatsPermission_btn.setEnabled(false);
                    usageStatsPermission_btn.setText("권한설정 완료");
                } else {
                    usageStatsPermission_btn.setEnabled(true);
                }
            }
        });

        // 다른 앱 위에 표시
        canDrawOverlaysPermission_btn = findViewById(R.id.canDrawOverlaysPermission_btn);
        canDrawOverlaysPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnlyPermission.checkCanDrawOverlays(false, requestCode_canDrawOverlaysPermission)) {
                    canDrawOverlaysPermission_btn.setEnabled(false);
                    canDrawOverlaysPermission_btn.setText("권한설정 완료");
                } else {
                    canDrawOverlaysPermission_btn.setEnabled(true);
                }
            }
        });

        // 권한 설정 여부에 따른 버튼 비활성화
        if (mOnlyPermission.checkSystemPermission(PERMISSIONS, true, requestCode_systemPermission)) {
            systemPermission_btn.setEnabled(false);
            systemPermission_btn.setText("권한설정 완료");
        }

        if (mOnlyPermission.checkPowerManagerPermission(true, requestCode_powerManagerPermission)) {
            powerManagerPermission_btn.setEnabled(false);
            powerManagerPermission_btn.setText("권한설정 완료");
        }

        if (mOnlyPermission.checkUsageStatsPermission(true, requestCode_usageStatsPermission)) {
            usageStatsPermission_btn.setEnabled(false);
            usageStatsPermission_btn.setText("권한설정 완료");
        }

        if (mOnlyPermission.checkCanDrawOverlays(true, requestCode_canDrawOverlaysPermission)) {
            canDrawOverlaysPermission_btn.setEnabled(false);
            canDrawOverlaysPermission_btn.setText("권한설정 완료");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            // System 권한
            case requestCode_systemPermission:
                if (mOnlyPermission != null && systemPermission_btn != null) {
                    if (mOnlyPermission.checkSystemPermission(PERMISSIONS, true, requestCode_systemPermission)) {
                        systemPermission_btn.setEnabled(false);
                        systemPermission_btn.setText("권한설정 완료");
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // 배터리 사용량 최적화 권한
            case requestCode_powerManagerPermission:
                if (mOnlyPermission != null && powerManagerPermission_btn != null) {
                    if (mOnlyPermission.checkPowerManagerPermission(true, requestCode_powerManagerPermission)) {
                        powerManagerPermission_btn.setEnabled(false);
                        powerManagerPermission_btn.setText("권한설정 완료");
                    }
                }

                break;
            // 사용자 접근 허용
            case requestCode_usageStatsPermission:
                if (mOnlyPermission != null && usageStatsPermission_btn != null) {
                    if (mOnlyPermission.checkUsageStatsPermission(true, requestCode_usageStatsPermission)) {
                        usageStatsPermission_btn.setEnabled(false);
                        usageStatsPermission_btn.setText("권한설정 완료");
                    }
                }

                break;
            // 다른 앱 위에 표시
            case requestCode_canDrawOverlaysPermission:
                if (mOnlyPermission != null && canDrawOverlaysPermission_btn != null) {
                    if (mOnlyPermission.checkCanDrawOverlays(true, requestCode_canDrawOverlaysPermission)) {
                        canDrawOverlaysPermission_btn.setEnabled(false);
                        canDrawOverlaysPermission_btn.setText("권한설정 완료");
                    }
                }

                break;
        }
    }
}