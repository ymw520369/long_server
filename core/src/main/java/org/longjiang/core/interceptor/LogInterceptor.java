package org.longjiang.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import org.alan.mars.kafka.producer.LogProducer;
import org.alan.mars.kafka.record.Record;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.longjiang.core.log.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2017/6/5.
 *
 * @author Alan
 * @since 1.0
 */
//申明是个切面
@Aspect
//申明是个spring管理的bean
@Component
@Order(1)
public class LogInterceptor {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogProducer logProducer;

    //申明一个切点 里面是 execution表达式
    @Pointcut("execution(public * org.longjiang.*.web.controller.*.*(..))")
    private void controllerAspect() {
    }

    //请求method前打印内容
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {

    }

    //在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturing(JoinPoint joinPoint, Object o) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        log.info("===============请求内容===============");
        log.info("请求地址:" + request.getRequestURL().toString());
        log.info("请求方式:" + request.getMethod());
        log.info("请求类方法:" + joinPoint.getSignature().getName());
        log.info("请求类方法参数:" + JSON.toJSONString(joinPoint.getArgs()));
        log.info("===============请求内容===============");
        log.info("===============返回内容===============");
        log.info("Response内容:" + JSON.toJSONString(o));
        log.info("===============返回内容===============");

        LogMessage logMessage = new LogMessage();
        logMessage.uri = request.getRequestURI();
        logMessage.method = request.getMethod();
        logMessage.classMethod = joinPoint.getSignature().getName();
        logMessage.requestArgs = joinPoint.getArgs();
        logMessage.response = o;
        logProducer.send(new Record("longjiang-log", logMessage));
        log.info("日志切面耗时 {}", stopwatch);
    }
}
