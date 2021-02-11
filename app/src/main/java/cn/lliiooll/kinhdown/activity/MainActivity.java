package cn.lliiooll.kinhdown.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.app.BaseApplication;
import cn.lliiooll.kinhdown.services.KDownloadServices;
import cn.lliiooll.kinhdown.ui.download.bridge.PlaceholderFragment;
import cn.lliiooll.kinhdown.utils.ActivityUtils;
import cn.lliiooll.kinhdown.utils.Toasts;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author lliiooll
 */
@Route(path = "/kinhdown/main")
public class MainActivity extends AppCompatActivity {

    public static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Button btn = findViewById(R.id.kinhdown_btn);
        View fragment = findViewById(R.id.nav_host_fragment);
        AppBarLayout bar = findViewById(R.id.kinhdown_bar);
        fragment.setPaddingRelative(fragment.getPaddingLeft(), fragment.getPaddingTop() + bar.getHeight(), fragment.getPaddingRight(), fragment.getPaddingBottom());
        //fragment.setPadding(fragment.getPaddingLeft(), fragment.getPaddingTop() + bar.getHeight(), fragment.getPaddingRight(), fragment.getPaddingBottom());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_file, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            //getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.action_bar));
        }
        ActivityUtils.register(this);
        INSTANCE = this;
        Intent intent = new Intent(BaseApplication.getApplication(), KDownloadServices.class);
        //启动servicce服务
        startService(intent);
        //navView.getMenu().getItem(R.id.navigation_home).getIcon().
    }


    private long time = 0;

    @Override
    public void onBackPressed() {
        long closeTime = 3000L;
        if (System.currentTimeMillis() - time > closeTime) {
            Toasts.showToast("再按一次返回键退出", Toast.LENGTH_SHORT);
            time = System.currentTimeMillis();
        } else {
            ActivityUtils.closeApp();
        }
    }
}