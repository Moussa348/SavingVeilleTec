package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Person;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Log
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @BeforeEach()
    void init(){
        List<Person> persons = Arrays.asList(
                Person.builder().email("francois@gmail.com").build()
        );

        personRepository.saveAll(persons);
    }

    @Test
    void existsByEmail(){
        //ARRANGE
        String email1 = "francois@gmail.com";
        String email2 = "adadasdasd";

        //ACT
        boolean personExistWithEmail1 = personRepository.existsByEmail(email1);
        boolean personExistWithEmail2 = personRepository.existsByEmail(email2);

        //ASSERT
        assertTrue(personExistWithEmail1);
        assertFalse(personExistWithEmail2);
    }
}
