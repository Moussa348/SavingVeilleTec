package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
                Article.builder().dailyExpense(dailyExpenses.get(0)).build(),
                Article.builder().dailyExpense(dailyExpenses.get(0)).build(),
                Article.builder().dailyExpense(dailyExpenses.get(0)).build()
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
}
