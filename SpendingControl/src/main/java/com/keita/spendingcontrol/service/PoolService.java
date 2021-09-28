package com.keita.spendingcontrol.service;

import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log
@Service
@Order(2)
@EnableScheduling
public class PoolService {

    private final DailyExpenseService dailyExpenseService;

    public PoolService(DailyExpenseService dailyExpenseService) {
        this.dailyExpenseService = dailyExpenseService;
    }

    @Scheduled(cron = "00 00 1 ? * *")
    public void createDailyExpenseForEveryPerson(){
        log.info("Nbr Of Daily Expenses created : " + dailyExpenseService.createDailyExpenseForEveryPerson());
    }
}
