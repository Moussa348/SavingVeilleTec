package com.keita.spendingcontrol;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.service.DailyExpenseService;
import com.keita.spendingcontrol.service.PersonService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Profile("test")
@Component
@Order(1)
@Log
@Transactional
public class DbInit implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private DailyExpenseService dailyExpenseService;

    private void createPersons(){
        List<Person> persons = Arrays.asList(
                Person.builder().id(1L).email("redaHamza@gmail.com").firstName("Reda").lastName("Hamza").password("reda123").build(),
                Person.builder().id(2L).email("franLacour@gmail.com").password("francois123").build()
        );

        persons.forEach(person -> {
            try {
                personService.createPerson(person);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void insertDailyExpenses(){
        log.info("nbr of dailyExpense created : " + dailyExpenseService.createDailyExpenseForEveryPerson());
    }

    @Override
    public void run(String... args) throws Exception {
        createPersons();
        insertDailyExpenses();
    }
}
