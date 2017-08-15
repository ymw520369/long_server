/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年2月22日
 */
package org.longjiang.core.result;


import org.longjiang.core.data.Protobuf;

/**
 * 本服务器设计用于通用消息返回格式
 *
 * @author Alan
 * @scene 1.0
 */
@Protobuf
public class JsonResult {
    public static final JsonResult SUCCESS() {
        return new JsonResult(0, "ok");
    }

    public static final JsonResult FAIL() {
        return new JsonResult(1, "fail");
    }

    public static final JsonResult ROLE_EXIST() {
        return new JsonResult(2, "role exist");
    }

    public static final JsonResult ROLE_NAME_REPEAT() {
        return new JsonResult(3, "role name repeat");
    }

    /**
     * 服务器响应码
     */
    private int code;
    /**
     * 相应数据
     */
    private Object data;
    /**
     * 相应描述信息
     */
    private String des;

    public JsonResult(int code) {
        super();
        this.code = code;
    }

    public JsonResult(int code, String des) {
        super();
        this.code = code;
        this.des = des;
    }

    public JsonResult(int code, Object data, String des) {
        super();
        this.code = code;
        this.data = data;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public JsonResult setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }

    public String getDes() {
        return des;
    }

    public JsonResult setDes(String des) {
        this.des = des;
        return this;
    }

}
