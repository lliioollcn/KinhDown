package cn.lliiooll.kinhdown.baidu.data;

import android.view.View;
import cn.hutool.core.util.RandomUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Data
public class BaiduCloudInfo {

    private Type type = Type.SHARE;

    private String fileName = "none";

    private String path = "";

    private boolean isFolder = false;

    public boolean first = false;

    private View.OnClickListener mOnClick;
    private View.OnLongClickListener mOnLongClick;

    private static Map<String, BaiduCloudInfo> maps = new HashMap<>();
    private View view;

    public static String save(BaiduCloudInfo info) {
        String key = RandomUtil.randomString(10);
        maps.put(key, info);
        return key;
    }

    public static BaiduCloudInfo get(String key) {
        if (maps.containsKey(key)) return maps.get(key);
        return new BaiduCloudInfo();
    }

    public static BaiduCloudInfo pull(String key) {
        if (maps.containsKey(key)) {
            BaiduCloudInfo info = maps.get(key);
            maps.remove(key);
            return info;
        }
        return new BaiduCloudInfo();
    }

    public View.OnClickListener getOnClickListenr() {
        return mOnClick;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        fileName = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setIsFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        if (isFolder){
            this.mOnClick = onClickListener;
        }
    }

    public BaiduCloudInfo() {

    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * 用于请求
     * @return
     */
    public Future<?> doRequest() {
        return null;
    }

    public enum Type {
        PRIVATE,// 登陆文件
        SHARE,// 分享文件
    }
}
