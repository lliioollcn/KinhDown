package cn.lliiooll.kinhdown.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.fab.DragFloatActionButtonFab;
import cn.lliiooll.kinhdown.fab.DragFloatActionMenu;
import cn.lliiooll.kinhdown.services.KDownloadServices;
import cn.lliiooll.kinhdown.ui.download.bridge.DownloadAdapter;
import cn.lliiooll.kinhdown.ui.download.bridge.PlaceholderFragment;
import cn.lliiooll.kinhdown.ui.download.bridge.SectionsPagerAdapter;
import cn.lliiooll.kinhdown.utils.ActivityUtils;
import cn.lliiooll.kinhdown.utils.Toasts;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.arialyy.aria.core.download.DownloadEntity;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lliiooll
 */
@Route(path = "/kinhdown/download")
public class DownloadActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }
        ActivityUtils.register(this);
        doInit();
    }

    private void doInit() {
        ImageButton back_btn = findViewById(R.id.down_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadActivity.this.onBackPressed();
            }
        });
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(0);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        DragFloatActionMenu button = findViewById(R.id.fab_menu_download);
        button.setClosedOnTouchOutside(true);
        DragFloatActionButtonFab fab_down_del_failed = findViewById(R.id.fab_down_del_failed);
        DragFloatActionButtonFab fab_down_restart_failed = findViewById(R.id.fab_down_restart_failed);
        DragFloatActionButtonFab fab_down_pause = findViewById(R.id.fab_down_pause);
        DragFloatActionButtonFab fab_down_continue = findViewById(R.id.fab_down_continue);
    }
}