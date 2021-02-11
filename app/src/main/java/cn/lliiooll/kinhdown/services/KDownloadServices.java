package cn.lliiooll.kinhdown.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;
import androidx.annotation.Nullable;
import cn.lliiooll.kinhdown.utils.Toasts;
import cn.lliiooll.kinhdown.utils.Utils;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.HttpOption;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.task.DownloadTask;

import java.util.*;

public class KDownloadServices extends Service {

    public static KDownloadServices service;
    public DownloadReceiver instance;
    private Map<String, List<OnTask>> runTask = new HashMap<>();
    private Map<String, List<OnTask>> start = new HashMap<>();
    private Map<String, List<OnTask>> compile = new HashMap<>();
    private Map<String, List<OnTask>> fail = new HashMap<>();
    private Map<String, List<OnTask>> cancel = new HashMap<>();
    private Map<String, List<OnTask>> stop = new HashMap<>();
    private final String notificationId = "KinhDown_DW";
    private final CharSequence notificationName = "KinhDown";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("KinhDown")
                .setContentText("请不要关闭此通知，以此保证后台下载正常进行");
        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = this;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1, getNotification());
        instance = Aria.download(this);
        instance.register();
        instance.resumeAllTask();
        addAllKey();
    }

    private void addAllKey() {
        List<DownloadEntity> entities = instance.getTaskList();
        if (entities != null) {
            for (DownloadEntity entity : entities) {
                instance.load(entity.getId()).setExtendField(Utils.getKey(entity));
            }
        }
    }

    public long addTask(String url, String fileName) {
        long taskID = instance.load(url)
                .setFilePath(Utils.createPath(getApplication(), fileName))
                .create();
        addAllKey();
        return taskID;
    }

    public long addTask(String url, String fileName, HttpOption option) {
        long taskID = instance.load(url)
                .setFilePath(Utils.createPath(getApplication(), fileName))
                .option(option)
                .create();
        addAllKey();
        return taskID;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance.unRegister();
    }

    @Download.onTaskStart
    public void onTaskStart(DownloadTask task) {
        DownloadEntity entity = task.getDownloadEntity();
        String key = instance.load(entity.getId()).getExtendField();
        if (key != null && start.containsKey(key)) {
            //  start.get(key).doTask(task);
        }
    }

    @Download.onTaskStop
    public void onTaskStop(DownloadTask task) {
        DownloadEntity entity = task.getDownloadEntity();
        String key = instance.load(entity.getId()).getExtendField();
        if (key != null && start.containsKey(key)) {
            //start.get(key).doTask(task);
        }
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        DownloadEntity entity = task.getDownloadEntity();
        String key = instance.load(entity.getId()).getExtendField();
        if (key != null && cancel.containsKey(key)) {
            // cancel.get(key).doTask(task);
        }
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        DownloadEntity entity = task.getDownloadEntity();
        String key = instance.load(entity.getId()).getExtendField();
        if (key != null && fail.containsKey(key)) {
            // fail.get(key).doTask(task);
        }
    }

    @Download.onTaskComplete
    public void onTaskComplete(DownloadTask task) {
        DownloadEntity entity = task.getDownloadEntity();
        String key = instance.load(entity.getId()).getExtendField();
        if (key != null && compile.containsKey(key)) {
            // compile.get(key).doTask(task);
        }
    }

    @Download.onTaskRunning
    public void onTaskRunning(DownloadTask task) {
        // Toasts.showShort(task.getDownloadEntity().getFileName() + "，正在下载");
        List<DownloadEntity> t = instance.getAllCompleteTask();
        List<DownloadEntity> r = instance.getDRunningTask();
        List<DownloadEntity> n = instance.getAllNotCompleteTask();
        List<DownloadEntity> a = instance.getTaskList();
        int tc = 0;
        int rc = 0;
        int nc = 0;
        int ac = 0;
        if (t != null && r != null && n != null) {
            tc = t.size();
            rc = r.size();
            nc = n.size();
            ac = a.size();
        }
        Log.e("KinhDown Debug Test", String.format("ac: %s#run: %s#nc: %s#t: %s", tc, rc, nc, ac));
        DownloadEntity entity = task.getDownloadEntity();
        Log.e("KinhDown Debug Test", Arrays.toString(runTask.keySet().toArray()));
        String key = instance.load(entity.getId()).getExtendField();
        if (key != null) {
            Log.e("KinhDown Debug Test", key);
            if (runTask.containsKey(key)) {
                List<OnTask> list = runTask.get(key);
                for (OnTask task1 : list) {
                    task1.doTask(task);
                }
            }
        }
    }

    public void setOnRunning(String key, OnTask task) {
        List<OnTask> list = new ArrayList<>();
        if (runTask.containsKey(key)) {
            list.addAll(runTask.get(key));
        }
        list.add(task);
        runTask.put(key, list);
    }

    public void setOnStart(String key, OnTask task) {
        // this.start.put(key, task);
    }

    public void setOnCompile(String key, OnTask task) {
        //this.compile.put(key, task);
    }

    public void setOnFail(String key, OnTask task) {
        //this.fail.put(key, task);
        List<OnTask> list = new ArrayList<>();
        if (fail.containsKey(key)) {
            list.addAll(fail.get(key));
        }
        list.add(task);
        fail.put(key, list);
    }

    public void setOnCancel(String key, OnTask task) {
        //this.cancel.put(key, task);
    }

    public void setOnStop(String key, OnTask task) {
        // this.stop.put(key, task);
        List<OnTask> list = new ArrayList<>();
        if (stop.containsKey(key)) {
            list.addAll(stop.get(key));
        }
        list.add(task);
        stop.put(key, list);
    }

    public interface OnTask {
        void doTask(DownloadTask task);
    }
}
