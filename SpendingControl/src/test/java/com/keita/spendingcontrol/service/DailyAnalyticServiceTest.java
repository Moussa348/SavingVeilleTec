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

import static org.junit.jupiter.api.Assertions.*;

@Log
@ExtendWith(MockitoExtension.class)
public class DailyAnalyticServiceTest {

    @InjectMocks
    DailyAnalyticService dailyAnalyticService;


    @Test
    void getTotalPrice(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).price(25.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).price(25.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).price(25.0f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).price(25.0f).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        ));

        //ACT
        Float totalPrice = dailyAnalyticService.getTotalPrice(dailyExpense);

        //ASSERT
        assertEquals(100.0f, totalPrice);
    }

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

        //ACT

        //ASSERT
    }

    @Test
    void getLessExpensiveArticle(){
        //ARRANGE

        //ACT

        //ASSERT
    }

    @Test
    void getMapLessExpensiveArticlesByUseFullness(){
        //ARRANGE

        //ACT

        //ASSERT
    }

    @Test
    void getMapTotalByUseFullness(){
        //ARRANGE

        //ACT

        //ASSERT
    }

}
