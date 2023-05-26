package org.dtf202.subscriberservice;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MyhasitaTest {

    public static void main(String[] args) throws InterruptedException {
        ScheduledFixedRateExample scheduledFixedRateExample = new ScheduledFixedRateExample();
//        while(true) {
            scheduledFixedRateExample.scheduleFixedRateTaskAsync();

//        }
    }
}
@EnableAsync
class ScheduledFixedRateExample {
    @Async
    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println(
                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(1000);
    }

}
