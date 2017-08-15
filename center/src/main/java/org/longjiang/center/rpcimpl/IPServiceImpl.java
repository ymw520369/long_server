/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.longjiang.center.rpcimpl;

import org.longjiang.center.config.CenterConfig;
import com.google.gson.JsonObject;
import org.alan.mars.rpc.server.RpcService;
import org.alan.mars.service.IPService;
import org.alan.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/3/17.
 * IPService 远程调用实现类
 *
 * @author Chow
 * @since 1.0
 */
@RpcService(IPService.class)
@Component
public class IPServiceImpl implements IPService {

    Logger log = LoggerFactory.getLogger(getClass());

    private final CenterConfig centerConfig;

    @Autowired
    public IPServiceImpl(CenterConfig centerConfig) {
        this.centerConfig = centerConfig;
    }

    @Override
    public String getCountryIdByIP(String ip) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("ip", ip);
            HttpUtils.HttpResponse httpResponse = HttpUtils.doPost(centerConfig.getIpInfoUrl(), params);
            if (!httpResponse.isOk()) {
                log.warn("通过ip获取国家ID失败,http响应码为 {}", httpResponse.getStatusCode());
                return "UNKNOWN";
            }
            JsonObject data = httpResponse.getJsonObject("data");
            if (StringUtils.isEmpty(data)) {
                log.warn("通过ip获取国家ID失败,data为空");
                return "UNKNOWN";
            }
            return data.get("country_id").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "UNKNOWN";
    }

}
