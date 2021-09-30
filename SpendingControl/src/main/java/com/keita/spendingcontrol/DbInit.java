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
import com.keita.spendingcontrol.util.FileUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Log
@Order(1)
@Component
@Profile("test")
public class DbInit implements CommandLineRunner {

    private final PersonRepository personRepository;

    private final DailyExpenseRepository dailyExpenseRepository;

    private final ArticleRepository articleRepository;

    public DbInit(PersonRepository personRepository,
                  DailyExpenseRepository dailyExpenseRepository,
                  ArticleRepository articleRepository) {
        this.personRepository = personRepository;
        this.dailyExpenseRepository = dailyExpenseRepository;
        this.articleRepository = articleRepository;
    }

    private void createPersons() throws IOException {
        List<Person> persons = Arrays.asList(
                //Person.builder().id(1L).picture(FileUtil.setDefaultProfilePicture()).email("developpeurspring@gmail.com").firstName("developpeur").lastName("spring").password("dev123").build(),
                Person.builder().id(1L).picture(FileUtil.setDefaultProfilePicture()).email("test@gmail.com").firstName("test").lastName("test").password("test1234").build()
        );

        persons.forEach(person -> {
            try {

                person.setAccountVerified(true);

                personRepository.save(person);

                DailyExpense dailyExpense = new DailyExpense(personRepository.save(person));

                dailyExpense.setTotal(12.25f);

                articleRepository.save(Article.builder()
                        .name("cereales")
                        .time(LocalDateTime.now())
                        .qty(1)
                        .price(12.25f)
                        .degreeOfUseFullness(DegreeOfUseFullness.LOW)
                        .dailyExpense(dailyExpenseRepository.save(dailyExpense)).build());
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
