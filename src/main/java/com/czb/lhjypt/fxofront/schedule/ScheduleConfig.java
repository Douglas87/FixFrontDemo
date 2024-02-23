package com.czb.lhjypt.fxofront.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("MyQuickfixLog");

    @Scheduled(cron = "0/5 * * * * *")
    public void MySchedule() {
        /*for (int i = 1; i <= 100; i++) {
            log.info(this.getClass().toString());
            log.info(ScheduleConfig.class.toString());
        }*/
    }
}
