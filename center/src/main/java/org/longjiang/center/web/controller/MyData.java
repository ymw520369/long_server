/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.longjiang.center.web.controller;

/**
 * Created on 2017/3/23.
 *
 * @author Alan
 * @since 1.0
 */
public class MyData {

    int id = 1001;

    String name = "alan";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
