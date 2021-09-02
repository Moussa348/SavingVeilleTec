package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DailyExpenseServiceTest {

    @Mock
    DailyExpenseRepository dailyExpenseRepository;

    @Mock
    ArticleService articleService;

    @Mock
    PersonService personService;

    @InjectMocks
    DailyExpenseService dailyExpenseService;

    @Test
    void createDailyExpenseForEveryPerson(){
        //ARRANGE
        List<Person> persons = Arrays.asList(
                Person.builder().build(),
                Person.builder().build(),
                Person.builder().build()
        );
        when(personService.getListPerson()).thenReturn(persons);

        //ACT
        Long nbrOfDailyExpenseCreated = dailyExpenseService.createDailyExpenseForEveryPerson();

        //ASSERT
        assertEquals(persons.size(),nbrOfDailyExpenseCreated);
    }

    @Test
    void addArticleToDailyExpense(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).build();
        ArticleDetail articleDetail = new ArticleDetail(Article.builder().dailyExpense(dailyExpense).build());
        when(dailyExpenseRepository.findById(dailyExpense.getId())).thenReturn(Optional.of(dailyExpense));
        when(articleService.createArticleForDailyExperience(articleDetail,dailyExpense)).thenReturn(new Article(articleDetail,dailyExpense));

        //ACT
        dailyExpenseService.addArticleToDailyExpense(articleDetail);

        //ASSERT
        assertEquals(1,dailyExpense.getArticles().size());
    }

    @Test
    void getTotalExpenseBetweenDatesForPerson(){
        //ARRANGE
        Person person = Person.builder().id(1L).email("francois@gmail.com").build();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(2);
        List<DailyExpense> dailyExpenses = Arrays.asList(
                DailyExpense.builder().date(start).total(20.0f).person(person).build(),
                DailyExpense.builder().date(start).total(20.0f).person(person).build(),
                DailyExpense.builder().date(end).total(20.0f).person(person).build(),
                DailyExpense.builder().date(end.minusDays(1)).total(20.0f).person(person).build()
        );
        when(dailyExpenseRepository.findAllByPersonIdAndDateBetween(person.getId(),start,end)).thenReturn(dailyExpenses);

        //ACT
        Float subTotal = dailyExpenseService.getTotalExpenseBetweenDatesForPerson(person,start,end);

        //ASSERT
        assertEquals(80.0f,subTotal);
    }
}
