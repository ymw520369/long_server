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
public class GeneralResult {
    public static final GeneralResult SUCCESS() {
        return new GeneralResult(0, "ok");
    }

    public static final GeneralResult FAIL() {
        return new GeneralResult(1, "fail");
    }

    public static final GeneralResult ROLE_EXIST() {
        return new GeneralResult(2, "role exist");
    }

    public static final GeneralResult ROLE_NAME_REPEAT() {
        return new GeneralResult(3, "role name repeat");
    }

    /**
     * 服务器响应码
     */
    private int code;
    /**
     * 相应数据
     */
    private transient Object obj;
    /**
     * 相应数据
     */
    private byte[] data;
    /**
     * 相应描述信息
     */
    private String des;

    public GeneralResult(int code) {
        super();
        this.code = code;
    }

    public GeneralResult(int code, String des) {
        super();
        this.code = code;
        this.des = des;
    }

    public GeneralResult(int code, Object obj, String des) {
        super();
        this.code = code;
        this.obj = obj;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public GeneralResult setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return obj;
    }

    public GeneralResult setData(Object obj) {
        this.obj = obj;
        return this;
    }

    public GeneralResult setPb(byte[] data) {
        this.data = data;
        return this;
    }

    public byte[] getPb() {
        return data;
    }

    public String getDes() {
        return des;
    }

    public GeneralResult setDes(String des) {
        this.des = des;
        return this;
    }

}
