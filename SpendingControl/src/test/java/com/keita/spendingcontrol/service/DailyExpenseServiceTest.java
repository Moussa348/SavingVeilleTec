package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.dto.DailyAnalytic;
import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

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

    @Mock
    DailyAnalyticService dailyAnalyticService;

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
        Integer nbrOfDailyExpenseCreated = dailyExpenseService.createDailyExpenseForEveryPerson();

        //ASSERT
        assertEquals(persons.size(),nbrOfDailyExpenseCreated);
    }

    @Test
    void createDailyExpenseOnPersonCreation(){
        //ARRANGE
        Person person = new Person();

        //ACT
        dailyExpenseService.createDailExpenseForPerson(person);
    }

    @Test
    void addArticleToDailyExpense(){
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).total(20f).build();
        ArticleDetail articleDetail = new ArticleDetail(Article.builder().price(20f).degreeOfUseFullness(DegreeOfUseFullness.LOW).dailyExpense(dailyExpense).build());
        when(dailyExpenseRepository.findByPersonIdAndDate(dailyExpense.getPerson().getId(),LocalDate.now())).thenReturn(Optional.of(dailyExpense));
        when(articleService.createArticleForDailyExpense(articleDetail,dailyExpense)).thenReturn(new Article(articleDetail,dailyExpense));

        //ACT
        dailyExpenseService.addArticleToDailyExpense(articleDetail);

        //ASSERT
        assertEquals(1,dailyExpense.getArticles().size());
        assertEquals(40f,dailyExpense.getTotal());
    }

    @Test
    void getDailyExpenseByDateForPerson(){
        //ARRANGE
        Long id = 1L;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        ));
        when(dailyExpenseRepository.findByPersonIdAndDate(id,dailyExpense.getDate())).thenReturn(Optional.of(dailyExpense));

        //ACT
        DailyExpenseDetail dailyExpenseDetail = dailyExpenseService.getDailyExpenseByDateForPerson(id,dailyExpense.getDate());

        //ASSERT
        assertNotNull(dailyExpenseDetail);
    }

    @Test
    void getDailyAnalytic(){
        //ARRANGE
        Long id = 1L;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).date(LocalDate.now()).person(Person.builder().id(1L).build()).build();

        dailyExpense.setArticles(Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        ));
        when(dailyExpenseRepository.findByPersonIdAndDate(id,dailyExpense.getDate())).thenReturn(Optional.of(dailyExpense));

        //ACT
        DailyAnalytic dailyAnalytic = dailyExpenseService.getDailyAnalytic(id,LocalDate.now());

        //ASSERT
        assertNotNull(dailyAnalytic);
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

    @Test
    void findByPersonIdAndDate() {
        //ARRANGE
        Long id = 1L;
        LocalDate date = LocalDate.now();

        when(dailyExpenseRepository.findByPersonIdAndDate(id, date)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(ResponseStatusException.class, () -> dailyExpenseService.findDailyExpenseByPersonIdAndDate(id, date));
    }

    @Test
    void findAllArticleByPerson() {
        //ARRANGE
        Person person = Person.builder().id(1L).email("francois@gmail.com").build();
        List<DailyExpense> dailyExpenses = Arrays.asList(
                DailyExpense.builder().total(20.0f).person(person).build(),
                DailyExpense.builder().total(20.0f).person(person).build(),
                DailyExpense.builder().total(20.0f).person(person).build(),
                DailyExpense.builder().total(20.0f).person(person).build()
        );
        dailyExpenses.forEach(dailyExpense -> {
            dailyExpense.getArticles().add(Article.builder().build());
            dailyExpense.getArticles().add(Article.builder().build());
        });

        when(dailyExpenseRepository.findAllByPersonId(person.getId())).thenReturn(dailyExpenses);
        //ACT
        List<Article> articles = dailyExpenseService.findAllArticleByPerson(person.getId());
        //ASSERT
        assertEquals(8,articles.size());
    }
}
