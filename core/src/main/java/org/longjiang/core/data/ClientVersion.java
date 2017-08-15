package org.longjiang.core.data;


/**
 * Created on 2017/6/6.
 *
 * @author Alan
 * @since 1.0
 */
public class ClientVersion {
    /* 平台*/
    public Platform platform;
    /* 渠道*/
    public String channel;
    /* 主版本号*/
    public int majorVersion;
    /* 次版本号*/
    public int subVersion;
    /* 小版本号*/
    public int microVersion;

    public void setPlatform(String platform) {
        this.platform = Platform.valueOf(platform.toUpperCase());
    }
}
