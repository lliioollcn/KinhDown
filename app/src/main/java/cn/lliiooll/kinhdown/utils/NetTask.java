package cn.lliiooll.kinhdown.utils;

import android.util.Log;
import cn.lliiooll.kinhdown.baidu.beans.BaiduCloudWXList;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.HashMap;

public class NetTask {

    public static String doGet(String url) {
        try {
            return new OkHttpClient().newCall(new Request.Builder()
                    .url(url)
                    .get()
                    .build()).execute().body().string();
        } catch (IOException e) {
            return "";
        }
    }

    @SneakyThrows
    public static <T> T doGet(String url, Class<T> clazz) {
        String jstr = doGet(url);
        T instance = clazz.newInstance();
        Log.d("KinhDown Debug NetTask", jstr);
        if (Utils.isJson(jstr)) {
            instance = JSON.parseObject(jstr, clazz);
        }
        return instance;
    }
}
