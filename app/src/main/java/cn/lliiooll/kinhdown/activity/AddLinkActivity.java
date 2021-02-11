package cn.lliiooll.kinhdown.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.cron.task.Task;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.baidu.beans.BaiduCloudWXList;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudInfo;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudShareInfo;
import cn.lliiooll.kinhdown.fab.DragFloatActionButtonFab;
import cn.lliiooll.kinhdown.fab.DragFloatActionMenu;
import cn.lliiooll.kinhdown.ui.dialog.KinhDownDialog;
import cn.lliiooll.kinhdown.ui.dialog.KinhDownLoader;
import cn.lliiooll.kinhdown.ui.download.bridge.SectionsPagerAdapter;
import cn.lliiooll.kinhdown.ui.file.FileFragment;
import cn.lliiooll.kinhdown.utils.ActivityUtils;
import cn.lliiooll.kinhdown.utils.Tasks;
import cn.lliiooll.kinhdown.utils.Toasts;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import lombok.SneakyThrows;

import java.util.concurrent.Future;

/**
 * @author lliiooll
 */
@Route(path = "/kinhdown/addlink")
public class AddLinkActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlnk);
        if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }
        ActivityUtils.register(this);
        doInit();
    }

    private void doInit() {
        ImageButton back_btn = findViewById(R.id.addlink_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLinkActivity.this.onBackPressed();
            }
        });
        BaiduCloudShareInfo info = new BaiduCloudShareInfo();
        // https://pan.baidu.com/share/init?surl=AqJyiW63drSXANbUgRnT7w
        EditText link_share_link = findViewById(R.id.link_share_link);
        // 3hrm
        EditText link_share_pass = findViewById(R.id.link_share_pass);
        Button link_btn = findViewById(R.id.link_button);
        link_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setSharePass(link_share_pass.getText().toString());
                info.setShareLink(link_share_link.getText().toString());
                info.setIsFolder(true);
                info.setFileName("测试文件");
                info.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().
                                build("/kinhdown/fileList")
                                .withString("key", BaiduCloudInfo.save(info))
                                .withString("path", "test")
                                .navigation();
                    }
                });
                KinhDownLoader loader = new KinhDownLoader.Builder(AddLinkActivity.this)
                        .setTitle("解析中..")
                        .setType(KinhDownLoader.Type.LineScale).create();
                loader.open();
                Tasks.run(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        Future<BaiduCloudWXList> result = info.doRequest();
                        while (!result.isDone() && !result.isCancelled()) {
                            Thread.sleep(1000L);
                        }
                        AddLinkActivity.this.runOnUiThread(new Runnable() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                loader.close();
                                DialogInterface.OnClickListener l = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FileFragment.shares.add(info);
                                        dialog.dismiss();
                                        AddLinkActivity.this.finish();
                                    }
                                };
                                new KinhDownDialog.Builder(AddLinkActivity.this)
                                        .setMessage("此版本为仅ui版本，无实际功能。")
                                        .setTitle("警告")
                                        .setPositiveButton("确定", l)
                                        .setNegativeButton("确定", l)
                                        .create().show();
                            }
                        });
                    }
                });
            }
        });

    }
}