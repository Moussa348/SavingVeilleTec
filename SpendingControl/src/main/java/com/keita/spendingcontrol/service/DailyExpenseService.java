package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.dto.DailyAnalytic;
import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log
public class DailyExpenseService {

    @Autowired
    private DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private DailyAnalyticService dailyAnalyticService;


    public void createDailExpenseForPerson(Person person) {
        dailyExpenseRepository.save(new DailyExpense(person));
    }

    //TODO --> call into pooler
    public Integer createDailyExpenseForEveryPerson() {
        AtomicInteger counter = new AtomicInteger();

        personService.getListPerson().forEach(person -> {
            log.info(person.getEmail());
            createDailExpenseForPerson(person);
            counter.getAndIncrement();
        });

        return counter.get();
    }

    public void addArticleToDailyExpense(ArticleDetail articleDetail) {
        DailyExpense dailyExpense = findDailyExpenseByPersonIdAndDate(articleDetail.getPersonId(), LocalDate.now());

        dailyExpense.getArticles().add(articleService.createArticleForDailyExpense(articleDetail, dailyExpense));

        dailyExpense.setTotal(articleDetail.getPrice() + dailyExpense.getTotal());

        dailyExpenseRepository.save(dailyExpense);
    }

    public DailyExpenseDetail getDailyExpenseByDateForPerson(Long id, LocalDate date) {
        DailyExpense dailyExpense = findDailyExpenseByPersonIdAndDate(id, date);
        return new DailyExpenseDetail(dailyExpense, articleService.getListArticleDetailForDailyExperience(dailyExpense.getId(),0),articleService.mapListArticleByDegreeOfUseFullness(dailyExpense.getArticles()));
    }

    public DailyAnalytic getDailyAnalytic(Long id, LocalDate date) {
        DailyExpense dailyExpense = findDailyExpenseByPersonIdAndDate(id, date);

        return new DailyAnalytic(
                dailyAnalyticService.getMapMostExpensiveArticlesByUseFullness(dailyExpense.getArticles()),
                dailyAnalyticService.getMapLessExpensiveArticlesByUseFullness(dailyExpense.getArticles()),
                dailyAnalyticService.getMostExpensiveArticle(dailyExpense.getArticles()),
                dailyAnalyticService.getLessExpensiveArticle(dailyExpense.getArticles()),
                dailyAnalyticService.getMapTotalByUseFullness(dailyExpense.getArticles()),
                dailyExpense.getTotal()
        );
    }

    public Float getTotalExpenseBetweenDatesForPerson(Person person, LocalDate start, LocalDate end) {
        return dailyExpenseRepository.findAllByPersonIdAndDateBetween(person.getId(), start, end).stream().map(DailyExpense::getTotal).reduce(0.0f, Float::sum);
    }

    public DailyExpense findDailyExpenseByPersonIdAndDate(Long id, LocalDate date) {
        return dailyExpenseRepository.findByPersonIdAndDate(id, date).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find daily expense with id : " + id + " ,and date : " + date));
    }


}
