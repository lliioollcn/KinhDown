package cn.lliiooll.kinhdown.baidu.beans;

import lombok.Data;

@Data
public class BaiduCloudWXList {

    /**
     * 数据
     */
    public BaiduCloudWXListData data;
    /**
     * 错误码
     */
    public int errno;
    /**
     * 未知
     */
    public int cfrom_id;
    /**
     * 未知
     */
    public int is_zombie;
    /**
     * 未知
     */
    public int vip_level;
    /**
     * 未知
     */
    public int vip_point;
    /**
     * 未知
     */
    public int vip_type;

    /**
     * 请求id
     */
    public long request_id;

    /**
     * 错误类型
     */
    public Object errtype;
    /**
     * 请求时间
     */
    public long server_time;

}
