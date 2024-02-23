package com.czb.lhjypt.fxofront;

import com.czb.lhjypt.fxofront.quickfix.FixInitiator;
import com.czb.lhjypt.fxofront.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        SpringContextUtil.getBean(FixInitiator.class).StartApp();
    }

}
