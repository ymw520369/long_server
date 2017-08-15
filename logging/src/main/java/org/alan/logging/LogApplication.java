package org.alan.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

/**
 * Created on 2017/5/18.
 *
 * @author Alan
 * @scene 1.0
 */
@SpringBootApplication
@ComponentScan("org.alan")
@Order(value = 1)
public class LogApplication implements CommandLineRunner {

    public Logger log = LoggerFactory.getLogger(getClass());

    int seq = 1;

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {

    }

}
