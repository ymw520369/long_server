package org.longjiang.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created on 2017/5/24.
 *
 * @author Alan
 * @since 1.0
 */
@ControllerAdvice(basePackages = "org.longjiang.center.org.longjiang.game.web.controller")
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {
    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        log.info("encrpty supports..");
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("before body write,o={}", o);
//        String key = "16bytelongstring";
//        return XXTEA.encrypt(JSON.toJSONString(o), key);
        return o;
    }

}
