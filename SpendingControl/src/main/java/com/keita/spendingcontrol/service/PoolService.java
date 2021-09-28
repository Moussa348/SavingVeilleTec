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

    private final PersonService personService;

    public PoolService(DailyExpenseService dailyExpenseService, PersonService personService) {
        this.dailyExpenseService = dailyExpenseService;
        this.personService = personService;
    }

    @Scheduled(cron = "00 02 1 ? * *")
    public void createDailyExpenseForEveryPerson(){
        log.info("NBR OF DAILY EXPENSES CREATED : " + dailyExpenseService.createDailyExpenseForEveryPerson());
    }

    @Scheduled(cron = "00 00 0 ? * *")
    public void deleteAllUnverifiedAccount(){
        log.info("NBR OF UNVERIFIED ACCOUNT DELETED : " + personService.deleteAllUnverifiedAccount());

    }
}
