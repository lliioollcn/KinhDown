package cn.lliiooll.kinhdown.utils;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lliiooll
 */
public class ActivityUtils {

    private final static Set<Activity> ACTIVITIES = new HashSet<>();

    public static void register(Activity activity) {
        ACTIVITIES.add(activity);
    }

    public static void closeApp() {
        for (Activity activity : ACTIVITIES) {
            activity.finish();
        }
    }
}
