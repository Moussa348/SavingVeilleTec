package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

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
    void createDailyExperienceForEveryPerson(){
        //ARRANGE
        List<Person> persons = Arrays.asList(
                new Person(),
                new Person(),
                new Person()
        );
        when(personService.getListPerson()).thenReturn(persons);

        //ACT
        dailyExpenseService.createDailyExperienceForEveryPerson();

        //ASSERT

    }
}
