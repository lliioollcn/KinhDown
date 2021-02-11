package cn.lliiooll.kinhdown.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.ui.dialog.KinhDownDialog;
import cn.lliiooll.kinhdown.utils.ActivityUtils;
import cn.lliiooll.kinhdown.utils.PermissionUtils;
import cn.lliiooll.kinhdown.utils.Tasks;
import cn.lliiooll.kinhdown.utils.Utils;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author lliiooll
 */
public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }
        ActivityUtils.register(this);
        doInit();
    }

    private void doInit() {
        Tasks.run(new Runnable() {
            @Override
            public void run() {
                boolean c = false;
                for (String per : PermissionUtils.pers) {
                    if (!PermissionUtils.hasPermission(per)) {
                        c = true;
                    }
                }
                if (c) {
                    StartActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {// 请求权限
                            //弹dialog请求权限
                            new KinhDownDialog.Builder(StartActivity.this)
                                    .setMessage("我们需要以下权限来保证KinhDown的正常运行:\n* 网络访问(用于下载文件)\n*文件储存(用于储存文件)\n*后台运行(退出后下载不停止)")
                                    .setNegativeButton("同意", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            PermissionUtils.requestPermissions(StartActivity.this);
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("拒绝并退出应用", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityUtils.closeApp();
                                        }
                                    })
                                    .setTitle("温馨提示").create().show();
                        }
                    });
                } else {
                    //for (int i = 0; i < 2; i++) {
                       // try {
                         //   Thread.sleep(1000L);
                      //  } catch (InterruptedException e) {
                       //     e.printStackTrace();
                      //  }
                   // }
                    doJump();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.closeApp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean q = true;
        if (requestCode == PermissionUtils.requestCode) {// 确定是我们发出的请求
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    new KinhDownDialog.Builder(StartActivity.this)
                            .setMessage("我们需要以下权限来保证KinhDown的正常运行:\n* 网络访问(用于下载文件)\n*文件储存(用于储存文件)\n*后台运行(退出后下载不停止)")
                            .setNegativeButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PermissionUtils.requestPermissions(StartActivity.this);
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("拒绝并退出应用", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityUtils.closeApp();
                                }
                            })
                            .setTitle("温馨提示").create().show();
                    q = false;
                    break;
                }
            }
            if (q) {
                doJump();
            }
        }
    }

    private void doJump() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Utils.isFirst(StartActivity.this)) {
                    Tasks.run(new Runnable() {
                        @Override
                        public void run() {
                            final String terms = Utils.getTerms();
                            StartActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new KinhDownDialog.Builder(StartActivity.this)
                                            .setMessage("欢迎使用KinhDown! " + terms)
                                            .setNegativeButton("同意", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ARouter.getInstance().build("/kinhdown/main").navigation();
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setPositiveButton("拒绝并退出应用", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ActivityUtils.closeApp();
                                                }
                                            })
                                            .setTitle("温馨提示").create().show();
                                }
                            });
                        }
                    });
                } else {
                    Tasks.run(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0;i<2;i++){
                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            StartActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ARouter.getInstance().build("/kinhdown/main").navigation();
                                }
                            });
                        }
                    });

                }
            }
        });

    }
}