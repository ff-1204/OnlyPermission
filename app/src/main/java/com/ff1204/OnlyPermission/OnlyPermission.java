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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class OnlyPermission {
    final String TAG = this.getClass().getSimpleName();

    protected Activity activity;
    protected Context context;

    public OnlyPermission(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    // System 권한
    protected boolean checkSystemPermission(String[] PERMISSIONS, boolean isOnlyPermissionCheck, int requestCode) {
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.clear();
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                Log.e(TAG, "no " + permission);
                permissionList.add(permission);
            } else {
                Log.e(TAG, permission);
            }
        }

        if (permissionList.size() > 0) {
            String[] array = new String[permissionList.size()];
            int size = 0;
            for (String temp : permissionList) {
                array[size++] = temp;
            }

            boolean isPermission = true;
            for (String permission : array) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    isPermission = (isPermission && false);
                }
            }

            if (!isPermission) {
                if (!isOnlyPermissionCheck) {
                    ActivityCompat.requestPermissions(activity, array, requestCode);
                }
            }

            return false;
        } else {
            return true;
        }
    }

    // 배터리 사용량 최적화 중지
    @SuppressLint("BatteryLife")
    protected boolean checkPowerManagerPermission(boolean isOnlyPermissionCheck, int requestCode) {
        String packageName = context.getPackageName();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            if (!isOnlyPermissionCheck) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                activity.startActivityForResult(intent, requestCode);
            }

            return false;
        } else {
            return true;
        }
    }

    // 사용자 접근 허용
    protected boolean checkUsageStatsPermission(boolean isOnlyPermissionCheck, int requestCode) {
        boolean granted;
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT)
            granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        else granted = (mode == AppOpsManager.MODE_ALLOWED);

        if (!granted) {
            if (!isOnlyPermissionCheck) {
                activity.startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), requestCode);
            }

            return false;
        } else {
            return true;
        }
    }

    // 다른 앱 위에 표시
    protected boolean checkCanDrawOverlays(boolean isOnlyPermissionCheck, int requestCode) {
        String packageName = context.getPackageName();
        if (!Settings.canDrawOverlays(context)) {
            if (!isOnlyPermissionCheck) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + packageName));
                activity.startActivityForResult(intent, requestCode);
            }

            return false;
        } else {
            return true;
        }
    }

}
