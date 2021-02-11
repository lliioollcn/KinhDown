package cn.lliiooll.kinhdown.utils;

import android.content.Context;
import android.widget.Toast;
import cn.lliiooll.kinhdown.app.BaseApplication;

/**
 * @author lliiooll
 */
public class Toasts {
    public static void showToast(CharSequence s, int lengthLong) {
        Toast.makeText(BaseApplication.getApplication(),s,lengthLong).show();
    }

    public static void showShort(CharSequence s) {
        showToast(s,Toast.LENGTH_SHORT);
    }
}
