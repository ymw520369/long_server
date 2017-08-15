package org.longjiang.test;

import com.alibaba.fastjson.JSON;
import org.alan.mars.rpc.protocol.SerializationUtil;
import org.alan.utils.HttpUtils;
import org.longjiang.core.result.GeneralResult;
import org.longjiang.game.web.been.player.module.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/6/2.
 *
 * @author Alan
 * @since 1.0
 */
public class CreateRoleTest {
    public static void main(String[] args) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/protobuf");
        headers.put("session", "session1");

        String json = "{\"accountId\": 1001,\"token\": \"token-1\",\"roleName\": \"你的名字\" }";


//        GameEnterController.CreateRole createRole = new GameEnterController.CreateRole();
//
//        createRole.accountId = (int) (Math.random() * 10000);
//        createRole.roleName = "alan" + createRole.accountId;
//        createRole.token = "asdasdas";
//
//        byte[] data = SerializationUtil.serialize(createRole);
//
//        HttpUtils.HttpResponse httpResponse = HttpUtils.
//                doPost("http://192.168.199.123:8082/enter/create", data, headers);
//        GeneralResult result = SerializationUtil.deserialize(httpResponse.getResponseBody(), GeneralResult.class);
//        if (result.getPb() != null) {
//            result.setData(SerializationUtil.deserialize(result.getPb(), Role.class));
//        }
//        System.out.println(JSON.toJSON(result));

        System.out.println("/////////下面是查询信息//////////");

        Long roleUid = new Long(10201);
        byte[] data = SerializationUtil.serialize(roleUid);
        HttpUtils.HttpResponse httpResponse = HttpUtils.
                doPost("http://192.168.199.123:8082/enter/getRole", data, headers);
        GeneralResult result = SerializationUtil.deserialize(httpResponse.getResponseBody(), GeneralResult.class);
        if (result.getPb() != null) {
            result.setData(SerializationUtil.deserialize(result.getPb(), Role.class));
        }
        System.out.println(JSON.toJSON(result));
    }
}
