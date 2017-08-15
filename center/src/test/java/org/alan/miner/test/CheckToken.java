package org.alan.miner.test;

import org.alan.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/6/2.
 *
 * @author Alan
 * @since 1.0
 */
public class CheckToken {
    public static void main(String[] args) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("platform", "IOS");
        params.put("majorVersion", "1");
        params.put("subVersion", "1");
        params.put("microVersion", "1");
        params.put("channel", "1");

        String json = "{\"platform\": \"IOS\",\"majorVersion\": \"1\",\"subVersion\": \"1\",\"microVersion\": \"1\",\"channel\": \"1\" }";

        HttpUtils.HttpResponse httpResponse = HttpUtils.
                doPost("http://192.168.199.247:8081/version/check", json, headers, "UTF8");
//        String result = SerializationUtil.deserialize(httpResponse.getResponseBody(), String.class);
        System.out.println(httpResponse);
    }
}
