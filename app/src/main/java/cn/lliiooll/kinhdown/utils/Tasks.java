package cn.lliiooll.kinhdown.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Tasks {
    static final ExecutorService taskPool = new ThreadPoolExecutor(2, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(512), new ThreadFactoryBuilder().setNameFormat("KinhDownServices-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    public static void run(Runnable runnable) {
        taskPool.execute(runnable);
    }

    public static ExecutorService getPool() {
        return Tasks.taskPool;
    }

}
