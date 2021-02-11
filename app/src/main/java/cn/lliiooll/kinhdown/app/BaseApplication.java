package cn.lliiooll.kinhdown.app;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.HttpOption;

/**
 * @author lliiooll
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;
    private final static boolean DEBUG = true;

    public static BaseApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if (isDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        Aria.init(this);
    }

    public static boolean isDebug() {
        return DEBUG;
    }
}
