package org.longjiang.center.config;

import org.longjiang.core.data.Platform;

/**
 * Created on 2017/6/2.
 *
 * @author Alan
 * @since 1.0
 */
public class Version {
    /* 平台*/
    public Platform platform;
    /* 主版本号 大功能更新*/
    public int majorVersion;
    /* 次版本号 小功能更新或bug修改*/
    public int subVersion;
    /* 小版本号 资源版本号*/
    public int microVersion;
    /* 是否强制更新*/
    public boolean forced;

    /* 主版本号 审核专用*/
    public int approvalMajorVersion;
    /* 次版本号 审核专用*/
    public int approvalSubVersion;
    /* 小版本号 审核专用*/
    public int approvalMicroVersion;

    /* 服务器更新地址*/
    public String serverUpdateUrl;
    /* 客户端APP更新地址*/
    public String appUpdateUrl;
    /* 客户端资源更新地址*/
    public String resourceUpdateUrl;
    /* 审核服资源更新地址*/
    public String approvalResourceUpdateUrl;

}
