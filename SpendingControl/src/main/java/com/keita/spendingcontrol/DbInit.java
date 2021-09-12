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

import java.util.Arrays;
import java.util.List;

@Profile("test")
@Component
@Order(1)
@Log
public class DbInit implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    private void createPersons(){
        List<Person> persons = Arrays.asList(
                Person.builder().id(1L).email("developpeurspring@gmail.com").firstName("developpeur").lastName("spring").password("dev123").build()
        );

        persons.forEach(person -> {
            try {
                personService.createPerson(person);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                person.setAccountVerified(true);
            }
        });
    }

    @Override
    public void run(String... args) throws Exception {
        createPersons();
    }
}
