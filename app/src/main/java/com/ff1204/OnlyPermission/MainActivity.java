/*
MIT License

Copyright (c) 2021 ff-1204

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
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

        // System ??????
        systemPermission_btn = findViewById(R.id.systemPermission_btn);
        systemPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systemPermission_btn.setEnabled(false);
                if (mOnlyPermission.checkSystemPermission(PERMISSIONS, false, requestCode_systemPermission)) {
                    systemPermission_btn.setEnabled(false);
                    systemPermission_btn.setText("???????????? ??????");
                } else {
                    systemPermission_btn.setEnabled(true);

                }
            }
        });

        // ????????? ????????? ????????? ??????
        powerManagerPermission_btn = findViewById(R.id.powerManagerPermission_btn);
        powerManagerPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnlyPermission.checkPowerManagerPermission(false, requestCode_powerManagerPermission)) {
                    powerManagerPermission_btn.setEnabled(false);
                    powerManagerPermission_btn.setText("???????????? ??????");
                } else {
                    powerManagerPermission_btn.setEnabled(true);
                }
            }
        });

        // ????????? ?????? ??????
        usageStatsPermission_btn = findViewById(R.id.usageStatsPermission_btn);
        usageStatsPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnlyPermission.checkUsageStatsPermission(false, requestCode_usageStatsPermission)) {
                    usageStatsPermission_btn.setEnabled(false);
                    usageStatsPermission_btn.setText("???????????? ??????");
                } else {
                    usageStatsPermission_btn.setEnabled(true);
                }
            }
        });

        // ?????? ??? ?????? ??????
        canDrawOverlaysPermission_btn = findViewById(R.id.canDrawOverlaysPermission_btn);
        canDrawOverlaysPermission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnlyPermission.checkCanDrawOverlays(false, requestCode_canDrawOverlaysPermission)) {
                    canDrawOverlaysPermission_btn.setEnabled(false);
                    canDrawOverlaysPermission_btn.setText("???????????? ??????");
                } else {
                    canDrawOverlaysPermission_btn.setEnabled(true);
                }
            }
        });

        // ?????? ?????? ????????? ?????? ?????? ????????????
        if (mOnlyPermission.checkSystemPermission(PERMISSIONS, true, requestCode_systemPermission)) {
            systemPermission_btn.setEnabled(false);
            systemPermission_btn.setText("???????????? ??????");
        }

        if (mOnlyPermission.checkPowerManagerPermission(true, requestCode_powerManagerPermission)) {
            powerManagerPermission_btn.setEnabled(false);
            powerManagerPermission_btn.setText("???????????? ??????");
        }

        if (mOnlyPermission.checkUsageStatsPermission(true, requestCode_usageStatsPermission)) {
            usageStatsPermission_btn.setEnabled(false);
            usageStatsPermission_btn.setText("???????????? ??????");
        }

        if (mOnlyPermission.checkCanDrawOverlays(true, requestCode_canDrawOverlaysPermission)) {
            canDrawOverlaysPermission_btn.setEnabled(false);
            canDrawOverlaysPermission_btn.setText("???????????? ??????");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            // System ??????
            case requestCode_systemPermission:
                if (mOnlyPermission != null && systemPermission_btn != null) {
                    if (mOnlyPermission.checkSystemPermission(PERMISSIONS, true, requestCode_systemPermission)) {
                        systemPermission_btn.setEnabled(false);
                        systemPermission_btn.setText("???????????? ??????");
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // ????????? ????????? ????????? ??????
            case requestCode_powerManagerPermission:
                if (mOnlyPermission != null && powerManagerPermission_btn != null) {
                    if (mOnlyPermission.checkPowerManagerPermission(true, requestCode_powerManagerPermission)) {
                        powerManagerPermission_btn.setEnabled(false);
                        powerManagerPermission_btn.setText("???????????? ??????");
                    }
                }

                break;
            // ????????? ?????? ??????
            case requestCode_usageStatsPermission:
                if (mOnlyPermission != null && usageStatsPermission_btn != null) {
                    if (mOnlyPermission.checkUsageStatsPermission(true, requestCode_usageStatsPermission)) {
                        usageStatsPermission_btn.setEnabled(false);
                        usageStatsPermission_btn.setText("???????????? ??????");
                    }
                }

                break;
            // ?????? ??? ?????? ??????
            case requestCode_canDrawOverlaysPermission:
                if (mOnlyPermission != null && canDrawOverlaysPermission_btn != null) {
                    if (mOnlyPermission.checkCanDrawOverlays(true, requestCode_canDrawOverlaysPermission)) {
                        canDrawOverlaysPermission_btn.setEnabled(false);
                        canDrawOverlaysPermission_btn.setText("???????????? ??????");
                    }
                }

                break;
        }
    }
}
