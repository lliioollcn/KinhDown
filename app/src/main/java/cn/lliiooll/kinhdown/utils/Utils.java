package cn.lliiooll.kinhdown.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arialyy.aria.core.download.DownloadEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

import static android.content.ContentValues.TAG;

public class Utils {
    @SuppressLint("CommitPrefEdits")
    public static boolean isFirst(Activity activity) {
        SharedPreferences sa = activity.getSharedPreferences("kinhdown", 0);
        boolean is = sa.getBoolean("first", true);
        if (is) {
            sa.edit().putBoolean("first", false).apply();
        }
        return is;
    }

    public static String getTerms() {
        try {
            String content = NetTask.doGet("https://api.kinh.cc/KinhDown/Data/Terms.txt");
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "用户协议加载失败";
        }
    }


    public static String createPath(Context context, String s) {
        return getDiskCacheDir(context) + "/" + s;
    }

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * @param context
     * @param filePath relative path in Q, such as: "DCIM/" or "DCIM/dir_name/"
     *                 absolute path before Q
     * @return
     */
    private static Cursor searchTxtFromPublic(Context context, String filePath, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.e(TAG, "searchTxtFromPublic: fileName is null");
            return null;
        }
        if (!filePath.endsWith("/")) {
            filePath = filePath + "/";
        }

        String queryPathKey = MediaStore.Files.FileColumns.RELATIVE_PATH;
        String selection = queryPathKey + "=? and " + MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
        Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                new String[]{MediaStore.Files.FileColumns._ID, queryPathKey, MediaStore.Files.FileColumns.DISPLAY_NAME},
                selection,
                new String[]{filePath, fileName},
                null);

        return cursor;
    }

    public static String getFileSize(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (file < 1024) {
            fileSizeString = df.format((double) file) + "B";
        } else if (file < 1048576) {
            fileSizeString = df.format((double) file / 1024) + "K";
        } else if (file < 1073741824) {
            fileSizeString = df.format((double) file / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) file / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static String getKey(DownloadEntity entity) {
        return SecureUtil.sha256(
                entity.getKey() +
                        entity.getMd5Code()
        );
    }

    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , dp, context.getResources().getDisplayMetrics());


    }

    public static boolean isJson(String jstr) {
        try {
            JSONObject j = JSON.parseObject(jstr);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
