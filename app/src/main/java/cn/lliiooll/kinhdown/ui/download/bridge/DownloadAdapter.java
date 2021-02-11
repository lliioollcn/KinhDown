package cn.lliiooll.kinhdown.ui.download.bridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.activity.MainActivity;
import cn.lliiooll.kinhdown.app.BaseApplication;
import cn.lliiooll.kinhdown.services.KDownloadServices;
import cn.lliiooll.kinhdown.utils.Toasts;
import cn.lliiooll.kinhdown.utils.Utils;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.inf.IEntity;
import com.arialyy.aria.core.task.DownloadTask;

import java.util.List;

public class DownloadAdapter extends ArrayAdapter<DownloadEntity> {
    private int resoureId;


    public DownloadAdapter(Context context, int textViewResourceId, List<DownloadEntity> objects) {
        super(context, textViewResourceId, objects);
        resoureId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //DownloadEntity
        DownloadEntity entity = getItem(position);
        //
        //使用LayoutInflater来为这个子项加载我们传入的布局
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resoureId, parent, false);
        //获取ImageView,TextView实例
        final TextView name = view.findViewById(R.id.down_fileName);//文件名
        final TextView total = view.findViewById(R.id.down_total);//文件大小
        final TextView speed = view.findViewById(R.id.down_speed);//下载速度
        final ProgressBar progressBar = view.findViewById(R.id.down_progress_bar);//进度条
        final Button pause = view.findViewById(R.id.down_btn_pauseOrCont);//暂停/继续
        final Button del = view.findViewById(R.id.down_btn_del);//删除
        name.setText(entity.getFileName());
        progressBar.setMax(100);
        if (KDownloadServices.service != null) {
            KDownloadServices.service.setOnRunning(Utils.getKey(entity), new KDownloadServices.OnTask() {
                @Override
                public void doTask(DownloadTask task) {
                    if (task.getDownloadEntity().getState() == IEntity.STATE_RUNNING) {
                        speed.setText(task.getDownloadEntity().getConvertSpeed());
                        total.setText(Utils.getFileSize(task.getDownloadEntity().getCurrentProgress()) + "/" + task.getDownloadEntity().getConvertFileSize());
                        progressBar.setProgress(task.getDownloadEntity().getPercent(), true);
                        pause.setText("暂停");
                        pause.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DownloadEntity e = task.getDownloadEntity();
                                long taskId = e.getId();
                                KDownloadServices.service.instance.load(taskId).stop();
                            }
                        });
                        del.setText("删除");
                        del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DownloadEntity e = task.getDownloadEntity();
                                long taskId = e.getId();
                                KDownloadServices.service.instance.load(taskId).cancel();
                                KDownloadServices.service.instance.load(taskId).removeRecord();
                            }
                        });
                    }
                }
            });
            KDownloadServices.service.setOnStop(Utils.getKey(entity), new KDownloadServices.OnTask() {
                @Override
                public void doTask(DownloadTask task) {
                    if (task.getDownloadEntity().getState() == IEntity.STATE_STOP) {
                        pause.setText("继续");
                        pause.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DownloadEntity e = task.getDownloadEntity();
                                long taskId = e.getId();
                                KDownloadServices.service.instance.load(taskId).resume();
                            }
                        });
                    }
                }
            });
            KDownloadServices.service.setOnFail(Utils.getKey(entity), new KDownloadServices.OnTask() {
                @Override
                public void doTask(DownloadTask task) {
                    if (task.getDownloadEntity().getState() == IEntity.STATE_FAIL) {
                        pause.setText("重试");
                        pause.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DownloadEntity e = task.getDownloadEntity();
                                long taskId = e.getId();
                                KDownloadServices.service.instance.load(taskId).reStart();
                            }
                        });
                    }
                }
            });
        } else {
            Intent intent = new Intent(BaseApplication.getApplication(), KDownloadServices.class);
            MainActivity.INSTANCE.startService(intent);
            return getView(position, convertView, parent);
        }
        //调用setImageResource(),setText()设置显示的图片和文字
        //返回布局
        return view;
    }


}
