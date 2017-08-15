package org.longjiang.core.util;

/**
 * redis key生成器
 * <p>
 * Created on 2017/6/22.
 *
 * @author Alan
 * @since 1.0
 */
public class RedisKeyGen {
    public static final String sep = "/";

    public static String documentKeyName(String tableName, Object key) {
        return tableName + sep + key;
    }

}
