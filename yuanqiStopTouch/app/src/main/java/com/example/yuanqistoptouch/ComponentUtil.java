package com.example.yuanqistoptouch;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;


public class ComponentUtil {
    /**
     * 判断应用是否处于运行状态
     */
    public static boolean isApplicationRunning(Context context, String strPackageName) {
        if (context == null || TextUtils.isEmpty(strPackageName)) {
            return false;
        }

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        if (list == null || list.size() <= 0) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
            if (strPackageName.equals(runningAppProcessInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某一activity是否处于运行状态
     */
    public static boolean isActivityRunning(Context context, String strActivityName) {
        if (context == null || TextUtils.isEmpty(strActivityName)) {
            return false;
        }

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(Integer.MAX_VALUE);
        if (list == null || list.size() <= 0) {
            return false;
        }

        for (ActivityManager.RunningTaskInfo runningTaskInfo : list) {
            if (strActivityName.equals(runningTaskInfo.topActivity.toString()) || strActivityName.equals(runningTaskInfo.baseActivity.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断服务是否处于运行状态
     */
    public static boolean isServiceRunning(Context context, String strServiceClassName) {
        if (context == null || TextUtils.isEmpty(strServiceClassName)) {
            return false;
        }

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = manager.getRunningServices(Integer.MAX_VALUE);
        if (list == null || list.size() <= 0) {
            return false;
        }

        for (ActivityManager.RunningServiceInfo runningServiceInfo : list) {
            if (strServiceClassName.equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInstallTerminalServiceApp(Context context) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> list = manager.getInstalledPackages(0);
        if (list == null || list.size() <= 0) {
            return false;
        }

        for (PackageInfo packageInfo : list) {
            if (StaticConfig.SERVER.PACKAGE_NAME.equals(packageInfo.packageName)) {
                return true;
            }
        }
        return false;
    }
}
