package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Person;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Log
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @BeforeAll()
    void init(){
        List<Person> persons = Arrays.asList(
                Person.builder().email("francois@gmail.com").password("francois123").build(),
                Person.builder().email("dsadasd@gmail.com").build(),
                Person.builder().email("erwwerwer@gmail.com").build()
        );

        persons.get(1).setActive(false);
        persons.get(2).setActive(false);

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

    @Test
    void findAllByActiveTrue(){
        //ACT
        List<Person> persons = personRepository.findAllByActiveTrue();

        //ASSERT
        assertEquals(1,persons.size());
    }

    @Test
    void findByEmailAndPassword(){
        //ARRANGE
        String email1 = "francois@gmail.com";
        String password1 = "francois123";

        String email2 = "adadasdasd";
        String password2 = "adadasdasd";

        //ACT
        boolean personExistWithEmailAndPassword1 = personRepository.findByEmailAndPassword(email1,password1).isPresent();
        boolean personExistWithEmailAndPassword2 = personRepository.findByEmailAndPassword(email2,password2).isPresent();

        //ASSERT
        assertTrue(personExistWithEmailAndPassword1);
        assertFalse(personExistWithEmailAndPassword2);
    }
}
