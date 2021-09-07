package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Log
public class ArticleRepositoryTest {

    @Autowired
    DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach()
    void init(){
        List<DailyExpense> dailyExpenses = Arrays.asList(
                DailyExpense.builder().id(1L).build()
        );
        dailyExpenseRepository.saveAll(dailyExpenses);

        List<Article> articles = Arrays.asList(
                Article.builder().degreeOfUtility(DegreeOfUtility.LOW).dailyExpense(dailyExpenses.get(0)).build(),
                Article.builder().degreeOfUtility(DegreeOfUtility.LOW).dailyExpense(dailyExpenses.get(0)).build(),
                Article.builder().degreeOfUtility(DegreeOfUtility.MEDIUM).dailyExpense(dailyExpenses.get(0)).build()
        );

        articleRepository.saveAll(articles);
    }

    @Test
    void findAllByDailyExpenseId(){
        //ARRANGE
        Long id = 1L;

        //ACT
        List<Article> articles = articleRepository.findAllByDailyExpenseId(id);

        //ASSERT
        assertEquals(3,articles.size());
    }

    @Test
    void findAllByDailyExpenseIdAndDegreeOfUtility(){
        //ARRANGE
        Long id = 1L;
        DegreeOfUtility degreeOfUtility = DegreeOfUtility.LOW;

        //ACT
        List<Article> articles = articleRepository.findAllByDailyExpenseIdAndDegreeOfUtility(id,degreeOfUtility, PageRequest.of(0,30, Sort.by("time").descending()));

        //ASSERT
        assertEquals(2,articles.size());
    }
}
