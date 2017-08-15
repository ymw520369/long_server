/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.longjiang.center.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/4/7.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "config")
public class CenterConfig {
    // ip 阿里查询url
    protected String ipInfoUrl;

    public String getIpInfoUrl() {
        return ipInfoUrl;
    }

    public void setIpInfoUrl(String ipInfoUrl) {
        this.ipInfoUrl = ipInfoUrl;
    }
}
