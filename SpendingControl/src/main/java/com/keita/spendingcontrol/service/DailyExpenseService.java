package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Log
public class DailyExpenseService {

    @Autowired
    private DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PersonService personService;


    public void createDailExpenseForPerson(Person person){
        dailyExpenseRepository.save(new DailyExpense(person));
    }
    //TODO --> call into pooler
    public Integer createDailyExpenseForEveryPerson(){
        AtomicInteger counter = new AtomicInteger();

        personService.getListPerson().forEach(person -> {
            log.info(person.getEmail());
            createDailExpenseForPerson(person);
            counter.getAndIncrement();
        });

        return counter.get();
    }

    public void addArticleToDailyExpense(ArticleDetail articleDetail){
        DailyExpense dailyExpense = findDailyExpenseByPersonIdAndDate(articleDetail.getPersonId(),LocalDate.now());

        dailyExpense.getArticles().add(articleService.createArticleForDailyExpense(articleDetail,dailyExpense));

        dailyExpenseRepository.save(dailyExpense);
    }

    public DailyExpenseDetail getDailyExpenseByDateForPerson(Long id,LocalDate date){
        DailyExpense dailyExpense = findDailyExpenseByPersonIdAndDate(id,date);
        return new DailyExpenseDetail(dailyExpense,articleService.mapListArticleByDegreeOfUseFullness(dailyExpense.getArticles()));
    }

    public Float getTotalExpenseBetweenDatesForPerson(Person person, LocalDate start,LocalDate end){
        return dailyExpenseRepository.findAllByPersonIdAndDateBetween(person.getId(),start,end).stream().map(DailyExpense::getTotal).reduce(0.0f, Float::sum);
    }

    private DailyExpense findDailyExpenseByPersonIdAndDate(Long id,LocalDate date){
        return dailyExpenseRepository.findByPersonIdAndDate(id,date).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't find daily expense with id : " + id + " ,and date : " + date));
    }

}
