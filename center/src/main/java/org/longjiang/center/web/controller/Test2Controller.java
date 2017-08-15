/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.longjiang.center.web.controller;

import org.longjiang.center.dao.PlatformAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.longjiang.center.user.PlatformAccMapping;

/**
 * Created on 2017/3/23.
 *
 * @author Alan
 * @since 1.0
 */
@RequestMapping("test2")
@RestController
public class Test2Controller {
    PlatformAccountDao platformAccountDao;

    @Autowired
    public Test2Controller(PlatformAccountDao platformAccountDao) {
        this.platformAccountDao = platformAccountDao;
    }

    @RequestMapping("t2")
    public String test2() {
        long beginTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < 100000000; i++) {
            sum++;
        }

        PlatformAccMapping pam = platformAccountDao.findByPfUserId("18785");
        long endTime = System.currentTimeMillis();
        return "test1 reloaded " + (endTime - beginTime) + ",sum=" + sum
                + ",pam" + pam.getPfUserName();
    }
}
