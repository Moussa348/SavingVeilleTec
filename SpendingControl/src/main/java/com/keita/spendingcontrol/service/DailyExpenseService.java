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

    @Autowired
    private PersonService personService;

    public Long createDailyExperienceForEveryPerson(){
        return personService.getListPerson().stream().map(person -> dailyExpenseRepository.save(new DailyExpense(person))).count();
    }

}
