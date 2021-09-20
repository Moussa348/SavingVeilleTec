package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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

}
