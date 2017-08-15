/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月18日 	
 */
package org.longjiang.center.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TSDK平台相关配置信息
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
@Component
@ConfigurationProperties(prefix = "tsdk")
public final class TsdkConfig {
	public String url = "http://192.168.2.200:8080/TSDK";
	public String certifyPath = "/user/verifyAccount";
	public String appKey = "7aadeb5300440ac86d59b3c7b3f8d901";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCertifyPath() {
		return certifyPath;
	}

	public void setCertifyPath(String certifyPath) {
		this.certifyPath = certifyPath;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}
