package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log
public class ArticleRepositoryTest {

    @Autowired
    DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeAll()
    void init() {

        List<DailyExpense> dailyExpenses = Arrays.asList(
                DailyExpense.builder().build()
        );
        dailyExpenseRepository.saveAllAndFlush(dailyExpenses);
        List<Article> articles = Arrays.asList(
                Article.builder().name("cereales").id(1L).time(LocalDateTime.now()).degreeOfUseFullness(DegreeOfUseFullness.LOW).dailyExpense(dailyExpenses.get(0)).build(),
                Article.builder().id(2L).time(LocalDateTime.now()).degreeOfUseFullness(DegreeOfUseFullness.LOW).dailyExpense(dailyExpenses.get(0)).build(),
                Article.builder().id(3L).time(LocalDateTime.now()).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).dailyExpense(dailyExpenses.get(0)).build()
        );

        articleRepository.saveAll(articles);
    }

    @Test
    void findAllByDailyExpenseId() {
        //ARRANGE
        Long id = 1L;

        //ACT
        List<Article> articles = articleRepository.findAllByDailyExpenseId(id,PageRequest.of(0,10,Sort.by("time").descending()));

        //ASSERT
        assertEquals(3, articles.size());
    }

    @Test
    void findByNameAndDailyExpenseId() {
        //ARRANGE
        String name = "cereales";
        Long id = 1L;

        //ACT
        boolean isArticlePresent = articleRepository.findByNameAndDailyExpenseId(name,id).isPresent();

        //ASSERT
        assertTrue(isArticlePresent);
    }

    @Test
    void findAllByDailyExpenseIdAndDegreeOfUseFullness() {
        //ARRANGE
        Long id = 1L;
        DegreeOfUseFullness degreeOfUseFullness = DegreeOfUseFullness.LOW;

        //ACT
        List<Article> articles = articleRepository.findAllByDailyExpenseIdAndDegreeOfUseFullness(id, degreeOfUseFullness, PageRequest.of(0, 30, Sort.by("time").descending()));

        //ASSERT
        assertEquals(2, articles.size());
    }
}
