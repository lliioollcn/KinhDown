package cn.lliiooll.kinhdown.baidu.beans;

import lombok.Data;

@Data
public class BaiduCloudFileInfo {

    /**
     * 未知
     */
    public int category;
    /**
     * 未知
     */
    public String fs_id;
    /**
     * 是否文件夹,1为文件夹
     */
    public String isdir;
    /**
     * 大小
     */
    public Object size;
    /**
     * 文件md5
     */
    public String md5;
    /**
     * 路径
     */
    public String path;
    /**
     * 文件名
     */
    public String server_filename;
    /**
     * 下载链接
     */
    public String dlink;

    /**
     * 未知
     */
    public long local_ctime;
    /**
     * 未知
     */
    public long server_mtime;
    /**
     * 未知
     */
    public long local_mtime;
    /**
     * 未知
     */
    public long server_ctime;

    /**
     * 错误类型
     */
    public int errtype;
    /**
     * 请求时间
     */
    public long server_time;

}
