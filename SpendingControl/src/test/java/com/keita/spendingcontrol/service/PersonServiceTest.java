package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import com.keita.spendingcontrol.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    @Test
    void createPerson() throws IOException {
        //ARRANGE
        Person person1 = Person.builder().email("araa@gmail.com").build();
        when(personRepository.existsByEmail(person1.getEmail())).thenReturn(false);

        Person person2 = Person.builder().email("dasdads@gmail.com").build();
        when(personRepository.existsByEmail(person2.getEmail())).thenReturn(true);

        //ACT
        boolean person1HasBeenCreated = personService.createPerson(person1);
        boolean person2HasBeenCreated = personService.createPerson(person2);

        //ASSERT
        assertTrue(person1HasBeenCreated);
        assertFalse(person2HasBeenCreated);
    }
}
