package com.keita.spendingcontrol.security;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthorizationServiceTest {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthorizationService authorizationService;

    @Test
    void isConnected() {
        //ARRANGE
        Person person = Person.builder().id(1L).roles("USER").build();
        String token = "Bearer " + jwtService.generate(person);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(jwtService.verify(token)));

        //ACT
        boolean isConnected = authorizationService.isConnected(person.getId());

        //ASSERT
        assertTrue(isConnected);
    }

    @Test
    void listBelongToUserConnected() {
        //ARRANGE
        Person person1 = Person.builder().id(1L).roles("USER").build();
        DailyExpense dailyExpense1 = DailyExpense.builder().id(1L).person(person1).build();
        List<ArticleDetail> articleDetails1 = Arrays.asList(
                new ArticleDetail(Article.builder().dailyExpense(dailyExpense1).degreeOfUseFullness(DegreeOfUseFullness.LOW).build()),
                new ArticleDetail(Article.builder().dailyExpense(dailyExpense1).degreeOfUseFullness(DegreeOfUseFullness.LOW).build())
        );
        String token = "Bearer " + jwtService.generate(person1);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(jwtService.verify(token)));

        //ACT
        boolean listBelongToUserConnected = authorizationService.listBelongToUserConnected(articleDetails1);

        //ASSERT
        assertTrue(listBelongToUserConnected);
    }

    @Test
    void listDoNotBelongToUserConnected() {
        //ARRANGE
        Person person1 = Person.builder().id(1L).roles("USER").build();
        Person person2 = Person.builder().id(2L).roles("USER").build();
        DailyExpense dailyExpense1 = DailyExpense.builder().id(1L).person(person1).build();
        List<ArticleDetail> articleDetails1 = Arrays.asList(
                new ArticleDetail(Article.builder().dailyExpense(dailyExpense1).degreeOfUseFullness(DegreeOfUseFullness.LOW).build()),
                new ArticleDetail(Article.builder().dailyExpense(dailyExpense1).degreeOfUseFullness(DegreeOfUseFullness.LOW).build())
        );
        String token = "Bearer " + jwtService.generate(person2);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(jwtService.verify(token)));

        //ACT
        boolean listBelongToUserConnected = authorizationService.listBelongToUserConnected(articleDetails1);

        //ASSERT
        assertFalse(listBelongToUserConnected);
    }
}
