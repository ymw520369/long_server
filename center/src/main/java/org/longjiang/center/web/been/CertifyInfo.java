/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月18日 	
 */
package org.longjiang.center.web.been;

import org.alan.utils.JsonUtils;

/**
 * 玩家TSDK登录信息数据结构
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class CertifyInfo {
	private String token;
	private String userId;
	private int platform;
	private String channel;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}

}
