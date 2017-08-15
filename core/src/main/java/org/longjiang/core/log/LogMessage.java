package org.longjiang.core.log;

import java.util.Arrays;
import java.util.Date;

/**
 * Created on 2017/6/9.
 *
 * @author Alan
 * @since 1.0
 */
public class LogMessage {
    public long offset;
    public long accountId;
    public long roleUid;
    public String roleName;
    public Date date;
    public String uri;
    public String method;
    public String classMethod;
    public Object[] requestArgs;
    public Object response;

    @Override
    public String toString() {
        return "LogMessage{" +
                "uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", requestArgs=" + Arrays.toString(requestArgs) +
                ", response=" + response +
                '}';
    }
}
