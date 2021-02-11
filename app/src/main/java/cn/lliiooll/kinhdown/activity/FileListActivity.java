package cn.lliiooll.kinhdown.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import cn.hutool.core.util.RandomUtil;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.baidu.beans.BaiduCloudFileInfo;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudInfo;
import cn.lliiooll.kinhdown.baidu.bridge.FileAdapter;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudShareInfo;
import cn.lliiooll.kinhdown.utils.ActivityUtils;
import cn.lliiooll.kinhdown.utils.Toasts;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lliiooll
 */
@Route(path = "/kinhdown/fileList")
public class FileListActivity extends Activity {

    public final List<BaiduCloudInfo> shares = new ArrayList<>();


    @Autowired(name = "path")
    public String path;
    @Autowired(name = "key")
    public String key;
    public BaiduCloudInfo info;

    private FileAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filelist);
        ARouter.getInstance().inject(this);
        if (getActionBar() != null && getActionBar().isShowing()) {
            getActionBar().hide();
        }
        ActivityUtils.register(this);
        doInit();
    }

    private void doInit() {
        info = BaiduCloudInfo.pull(key);
        ImageButton back_btn = findViewById(R.id.filelist_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileListActivity.this.onBackPressed();
            }
        });
        TextView pathView = findViewById(R.id.file_list_path);
        ListView listView = findViewById(R.id.filelist_list);
        BaiduCloudInfo back = new BaiduCloudInfo();
        back.setFileName("返回上一级");
        back.setIsFolder(true);
        back.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileListActivity.this.onBackPressed();
            }
        });
        shares.add(back);
        if (info instanceof BaiduCloudShareInfo) {
            String title = "测试标题";
            pathView.setText(title);

            BaiduCloudShareInfo sInfo1 = new BaiduCloudShareInfo();
            sInfo1.setPath("/test");
            sInfo1.doParse(null);
            sInfo1.doRequest();
            sInfo1.setIsFolder(true);
            sInfo1.setFileName("测试文件名");
            sInfo1.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().
                            build("/kinhdown/fileList")
                            .withString("key", BaiduCloudInfo.save(sInfo1))
                            .withString("path", path + sInfo1.getPath())
                            .navigation();
                }
            });
            sInfo1.setMOnLongClick(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sInfo1.setView(v);
                    showMenu(sInfo1);
                    return false;
                }
            });
            shares.add(sInfo1);

        }
        if (adapter == null) {
            adapter = new FileAdapter(this, R.layout.view_share_sub, shares);
        }
        listView.setAdapter(adapter);
    }


    public void showMenu(BaiduCloudInfo info) {
        PopupMenu popupMenu = new PopupMenu(this, info.getView());
        popupMenu.inflate(R.menu.file_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.file_menu_download:
                        Toasts.showShort("下载操作 - " + info.getFileName());
                        return true;
                    default:
                        //do nothing
                }

                return false;
            }
        });
        popupMenu.show();
    }

}