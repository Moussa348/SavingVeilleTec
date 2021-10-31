package com.keita.spendingcontrol.repository;


import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Log
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DailyExpenseRepositoryTest {

    @Autowired
    DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    PersonRepository personRepository;


    @BeforeAll
    void init(){
        Person person = Person.builder().id(1L).email("francois@gmail.com").build();

        personRepository.save(person);

        List<DailyExpense> dailyExpenses = Arrays.asList(
                DailyExpense.builder().date(LocalDate.now()).person(person).build(),
                DailyExpense.builder().date(LocalDate.now().plusDays(1)).person(person).build(),
                DailyExpense.builder().date(LocalDate.now().plusDays(2)).person(person).build(),
                DailyExpense.builder().date(LocalDate.now().plusDays(3)).person(person).build()
        );

        dailyExpenses.get(3).setDate(LocalDate.now().minusWeeks(3));

        dailyExpenseRepository.saveAll(dailyExpenses);
    }

    @Test
    void findAllByPersonIdAndDateBetween(){
        //ARRANGE
        Long id = 1L;
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(3);

        //ACT
        List<DailyExpense> dailyExpenses = dailyExpenseRepository.findAllByPersonIdAndDateBetween(id,date1,date2);

        //ASSERT
        assertEquals(3,dailyExpenses.size());
    }

    @Test
    void findByPersonIdAndDate(){
        //ARRANGE
        Long id = 1L;
        LocalDate date1 = LocalDate.now();

        //ACT
        boolean dailyExpenseFounded = dailyExpenseRepository.findByPersonIdAndDate(id,date1).isPresent();

        //ASSERT
        assertTrue(dailyExpenseFounded);
    }

    @Test
    void findAllByPersonId(){
        //ARRANGE
        Long id = 1L;

        //ACT
        List<DailyExpense> dailyExpenses = dailyExpenseRepository.findAllByPersonId(id);

        //ASSERT
        assertEquals(4,dailyExpenses.size());
    }
}
