/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.longjiang.center.web.controller;

import org.alan.mars.MarsContext;
import org.longjiang.center.dao.PlatformAccountDao;
import org.longjiang.center.user.PlatformAccMapping;
import org.alan.utils.RandomUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created on 2017/3/22.
 *
 * @author Alan
 * @since 1.0
 */
@RequestMapping("test")
@RestController
@Order(2)
public class TestController {

    PlatformAccountDao platformAccountDao;

    @RequestMapping("test1")
    public String test1() {
        long beginTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < 100000000; i++) {
            sum++;
        }

        if (platformAccountDao == null) {
            platformAccountDao = MarsContext.getBean(PlatformAccountDao.class);
        }

        PlatformAccMapping pam = platformAccountDao.findByPfUserId("18786");
        long endTime = System.currentTimeMillis();
        return "test1 reloaded " + (endTime - beginTime) + ",sum=" + sum
                + ",pam" + pam.getPfUserName() + ",mydata=" + new MyData();
    }

    @RequestMapping(value = "test2/{id}", method = RequestMethod.GET)
    public String test2(@PathVariable int id) {
        return "test2 load..." + id;
    }

    @RequestMapping(value = "test3", method = RequestMethod.POST, produces = "application/x-protobuf")
    public String test3(@RequestBody MyData obj) {
        System.out.println("body handle...");
        return "test3 load..." + obj;
    }

    @RequestMapping(value = "checkToken", method = RequestMethod.POST)
    public String checkToken(@RequestBody String token, HttpSession httpSession) {
        String key = RandomUtils.getRandomString(16);
        httpSession.setAttribute("xxteakey", key);
        return key;
    }
}
