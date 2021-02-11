package cn.lliiooll.kinhdown.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.lliiooll.kinhdown.app.BaseApplication;

/**
 * @author lliiooll
 */
public class PermissionUtils {

    public static final String[] pers = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.VIBRATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.FOREGROUND_SERVICE,
    };
    public static final int requestCode = 12138;

    /**
     * <!-- 用于网络访问 -->
     * <uses-permission android:name="android.permission.INTERNET"/>
     * <!-- 用于储存下载的文件 -->
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <!-- 用于读取文件的 -->
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     * <!-- 用于后台下载 -->
     * <uses-permission android:name="android.permission.WAKE_LOCK"/>
     * <!-- 用于下载完毕提醒(震动) -->
     * <uses-permission android:name="android.permission.VIBRATE"/>
     * <!-- 用于获取网络状态 -->
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * <!-- 读取手机IMEI的设备权限 -->
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static boolean hasPermission(String per) {
        return ContextCompat.checkSelfPermission(BaseApplication.getApplication(), per) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(activity,pers,PermissionUtils.requestCode);
        }
    }
}
