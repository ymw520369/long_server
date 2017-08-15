/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月27日 	
 */
package org.longjiang.test;

import com.google.gson.JsonObject;
import org.alan.utils.HttpUtils;
import org.alan.utils.HttpUtils.HttpResponse;
import org.alan.utils.MD5;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class CertifyTest {

	public static void main(String[] args) throws Exception {
		Map<String, String> param = new HashedMap<>();

		int appId = 2;
		int channelId = 2;
		int r = (int) (Math.random() * 1000);
		String username = "User" + r;
		String pwd = "pwd123456";
		String url = "http://192.168.2.200:8080/TSDK/";
		String email = "mingweiyang" + r + "@foxmail.com";
		param.put("appID", "" + appId);
		param.put("channelID", "" + channelId);
		param.put("username", username);
		param.put("psw", pwd);
		param.put("email", email);

		HttpResponse httpResponse = HttpUtils.doPost(url + "account/register",
				param);
		Integer state = httpResponse.getIntValue("state");
		if (state != null && state == 1) {
			System.out.println("1、注册成功");
		} else {
			System.out.println("1、注册失败");
			return;
		}
		param.put("psw", MD5.md5Digest(pwd));

		httpResponse = HttpUtils.doPost(url + "account/login", param);
		state = httpResponse.getIntValue("state");
		if (state != null && state == 1) {
			System.out.println("2、登录成功");
		} else {
			System.out.println("2、登录失败");
			return;
		}
		JsonObject jsonObject = httpResponse.getJsonObject("data");
		String userId = jsonObject.get("userID").getAsString();
		String token = jsonObject.get("token").getAsString();

		url = "http://192.168.2.21:8081/";
		param.put("userId", userId);
		param.put("token", token);
		httpResponse = HttpUtils.doPost(url + "user/certify", param);
		Integer code = httpResponse.getIntValue("code");
		System.out.println(httpResponse);
	}
}
