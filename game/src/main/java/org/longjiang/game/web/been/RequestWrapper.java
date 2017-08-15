package org.longjiang.game.web.been;

import org.longjiang.core.data.ClientVersion;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2017/6/6.
 *
 * @author Alan
 * @since 1.0
 */
public class RequestWrapper<T> {
    public String timestamp;
    public Class<?> typeOfT;
    public transient HttpServletRequest request;
    public String token;
    public T value;
    public String uri;
    public byte[] IV;
    public byte[] KEY;
    public long accountId;
    public long roleUid;
    public int zoneId;
    public ClientVersion version;

}
