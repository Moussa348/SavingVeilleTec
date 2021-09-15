package com.keita.spendingcontrol;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import com.keita.spendingcontrol.repository.ArticleRepository;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import com.keita.spendingcontrol.repository.PersonRepository;
import com.keita.spendingcontrol.service.DailyExpenseService;
import com.keita.spendingcontrol.service.PersonService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Profile("test")
@Component
@Order(1)
@Log
public class DbInit implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    private ArticleRepository articleRepository;

    private void createPersons(){
        List<Person> persons = Arrays.asList(
                Person.builder().id(1L).email("developpeurspring@gmail.com").firstName("developpeur").lastName("spring").password("dev123").build()
        );

        persons.forEach(person -> {
            try {

                person.setAccountVerified(true);

               articleRepository.save(Article.builder()
                        .name("cereales").id(1L)
                        .time(LocalDateTime.now())
                        .qty(1)
                        .price(12.25f)
                        .degreeOfUseFullness(DegreeOfUseFullness.LOW)
                        .dailyExpense(dailyExpenseRepository.save(new DailyExpense(personRepository.save(person)))).build());

               // personService.createPerson(person);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run(String... args) throws Exception {
        createPersons();
    }
}
