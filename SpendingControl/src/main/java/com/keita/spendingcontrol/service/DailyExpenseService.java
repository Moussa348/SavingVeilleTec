package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyExpenseService {

    @Autowired
    private DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    private ArticleService articleService;

    public void createDailyExperience(){
        dailyExpenseRepository.save(new DailyExpense());
    }

}
