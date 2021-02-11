package cn.lliiooll.kinhdown.baidu.data;

import android.view.View;
import cn.lliiooll.kinhdown.baidu.beans.BaiduCloudFileInfo;
import cn.lliiooll.kinhdown.baidu.beans.BaiduCloudWXList;
import cn.lliiooll.kinhdown.utils.NetTask;
import cn.lliiooll.kinhdown.utils.Tasks;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 分享信息
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class BaiduCloudShareInfo extends BaiduCloudInfo {
    /**
     * 完整分享链接
     */
    public String shareLink;

    private List<BaiduCloudFileInfo> fileList = new ArrayList<>();

    /**
     * 分享链接后面的东西
     */
    public String shareKey;
    /**
     * 分享密码
     */
    public String sharePass;

    public BaiduCloudWXList data;

    public void setShareLink(String link) {
        this.shareLink = link;
        this.shareKey = this.shareLink.replace("https://pan.baidu.com/share/init?surl=", "");
    }

    @Override
    public Future<BaiduCloudWXList> doRequest() {
        setFileName("文件夹测试");
        setIsFolder(true);
        setPath("/awa");
        return Tasks.getPool().submit(new Callable<BaiduCloudWXList>() {
            @Override
            public BaiduCloudWXList call() throws Exception {
                return new BaiduCloudWXList();
            }
        });
    }

    public void doParse(BaiduCloudShareInfo sInfo) {
        if (sInfo != null) {
            this.setShareLink(sInfo.getShareLink());
            this.setSharePass(sInfo.getSharePass());
            this.setShareKey(sInfo.getShareKey());
        }
    }
}
