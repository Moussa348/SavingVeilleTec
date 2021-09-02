package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DailyExpenseService {

    @Autowired
    private DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PersonService personService;

    public Long createDailyExpenseForEveryPerson(){
        return personService.getListPerson().stream().map(person -> dailyExpenseRepository.save(new DailyExpense(person))).count();
    }

    public void addArticleToDailyExpense(ArticleDetail articleDetail){
        DailyExpense dailyExpense = findDailyExpenseById(articleDetail.getDailyExpenseId());

        dailyExpense.getArticles().add(articleService.createArticleForDailyExperience(articleDetail,dailyExpense));

        dailyExpenseRepository.save(dailyExpense);
    }

    private DailyExpense findDailyExpenseById(Long id){
        return dailyExpenseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't find daily expense with id : " + id));
    }

}
