package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Log
@ExtendWith(MockitoExtension.class)
public class DailyAnalyticServiceTest {

    @InjectMocks
    DailyAnalyticService dailyAnalyticService;


    @Test
    void getMostExpensiveArticle(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().name("comcombre").dailyExpense(dailyExpense).price(45.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("cereales").dailyExpense(dailyExpense).price(45.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poire").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        ));

        //ACT
        ArticleDetail mostExpensiveArticle = dailyAnalyticService.getMostExpensiveArticle(dailyExpense.getArticles());

        //ASSERT
        assertEquals(45.0f,mostExpensiveArticle.getPrice());
    }

    @Test
    void getMapMostExpensiveArticlesByUseFullness(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().name("comcombre").dailyExpense(dailyExpense).price(45.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("cereales").dailyExpense(dailyExpense).price(22.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poire").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build(),
                Article.builder().name("steak").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.HIGH).build()
        ));

        //ACT
        Map<DegreeOfUseFullness,ArticleDetail> mostExpensiveArticlesByUseFullness = dailyAnalyticService.getMapMostExpensiveArticlesByUseFullness(dailyExpense.getArticles());

        //ASSERT
        assertEquals(3,mostExpensiveArticlesByUseFullness.size());
        assertEquals(45.0f,mostExpensiveArticlesByUseFullness.get(DegreeOfUseFullness.LOW).getPrice());
        assertEquals(20.0f,mostExpensiveArticlesByUseFullness.get(DegreeOfUseFullness.MEDIUM).getPrice());
        assertEquals(20.0f,mostExpensiveArticlesByUseFullness.get(DegreeOfUseFullness.HIGH).getPrice());
    }

    @Test
    void getLessExpensiveArticle(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().name("comcombre").dailyExpense(dailyExpense).price(45.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("cereales").dailyExpense(dailyExpense).price(45.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poire").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        ));

        //ACT
        ArticleDetail mostExpensiveArticle = dailyAnalyticService.getLessExpensiveArticle(dailyExpense.getArticles());

        //ASSERT
        assertEquals(20.0f,mostExpensiveArticle.getPrice());
    }

    @Test
    void getMapLessExpensiveArticlesByUseFullness(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().name("comcombre").dailyExpense(dailyExpense).price(45.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("cereales").dailyExpense(dailyExpense).price(22.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(14.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build(),
                Article.builder().name("steak").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.HIGH).build()
        ));

        //ACT
        Map<DegreeOfUseFullness,ArticleDetail> lessExpensiveArticlesByUseFullness = dailyAnalyticService.getMapLessExpensiveArticlesByUseFullness(dailyExpense.getArticles());

        //ASSERT
        assertEquals(3,lessExpensiveArticlesByUseFullness.size());
        assertEquals(22.0f,lessExpensiveArticlesByUseFullness.get(DegreeOfUseFullness.LOW).getPrice());
        assertEquals(14.0f,lessExpensiveArticlesByUseFullness.get(DegreeOfUseFullness.MEDIUM).getPrice());
        assertEquals(20.0f,lessExpensiveArticlesByUseFullness.get(DegreeOfUseFullness.HIGH).getPrice());
    }

    @Test
    void getMapTotalByUseFullness(){
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().name("comcombre").dailyExpense(dailyExpense).price(25.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("cereales").dailyExpense(dailyExpense).price(25.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build(),
                Article.builder().name("poulet").dailyExpense(dailyExpense).price(14.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build(),
                Article.builder().name("steak").dailyExpense(dailyExpense).price(20.0f).degreeOfUseFullness(DegreeOfUseFullness.HIGH).build()
        ));

        //ACT
        Map<DegreeOfUseFullness,Float> totalByUseFullness = dailyAnalyticService.getMapTotalByUseFullness(dailyExpense.getArticles());

        //ASSERT
        assertEquals(3,totalByUseFullness.size());
        assertEquals(50.0f,totalByUseFullness.get(DegreeOfUseFullness.LOW));
        assertEquals(34.0f,totalByUseFullness.get(DegreeOfUseFullness.MEDIUM));
        assertEquals(20.0f,totalByUseFullness.get(DegreeOfUseFullness.HIGH));
    }

}
