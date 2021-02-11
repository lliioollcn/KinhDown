package cn.lliiooll.kinhdown.baidu.beans;

import lombok.Data;


@Data
public class BaiduCloudWXListData {
    /**
     * 标题
     */
    public String title;
    /**
     * 未知
     */
    public String seckey;
    /**
     * 分享人名称
     */
    public String uname;

    /**
     * 未知
     */
    public int expiredType;
    /**
     * 创建时间
     */
    public long link_ctime;
    /**
     * 文件夹个数
     */
    public int fileNums;
    /**
     * 是否未加载完毕
     */
    public boolean has_more;
    /**
     * 文件列表
     */
    public BaiduCloudFileInfo[] list;
}
