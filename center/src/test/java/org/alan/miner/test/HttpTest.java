package org.alan.miner.test;

import org.alan.mars.rpc.protocol.SerializationUtil;
import org.longjiang.center.web.controller.MyData;
import org.alan.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/5/24.
 *
 * @author Alan
 * @since 1.0
 */
public class HttpTest {

    public static void main(String[] args) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-protobuf");

//        String postBody = "{\"id\":\"1001\"}";
//        System.out.println(Arrays.toString(postBody.getBytes()));
//
//        String key = "16bytelongstring";
//
//        byte[] data = XXTEA.encrypt(postBody, key);

//        System.out.println(Arrays.toString(data));
//
//        System.out.println(new String(XXTEA.decrypt(data,key),"UTF-8"));

        MyData myData = new MyData();
        myData.setId(101);
        myData.setName("ymw");
        byte[] data = SerializationUtil.serialize(myData);

        HttpUtils.HttpResponse httpResponse = HttpUtils.doPost("http://192.168.199.247:8081/test/test3", data, headers);
        String result = SerializationUtil.deserialize(httpResponse.getResponseBody(),String.class);
        System.out.println(result);
    }
}
