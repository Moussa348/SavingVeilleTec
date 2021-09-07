package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.model.dto.Dashboard;
import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    DailyExpenseService dailyExpenseService;

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


    @Test
    void setPicture() throws IOException {
        //ARRANGE
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file.jpg", "file.jpg", "image/jpeg", "taaa".getBytes());
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        Person person2 = Person.builder().id(2L).email("dasdads@gmail.com").build();
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("file.py", "file.py", "image/jpeg", "taaa".getBytes());
        when(personRepository.findById(person2.getId())).thenReturn(Optional.of(person2));

        //ACT
        personService.setPicture(person1.getId(), mockMultipartFile1);

        //Assert
        assertThrows(IOException.class, () -> personService.setPicture(person2.getId(), mockMultipartFile2));
    }

    @Test
    void disableAccount(){
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        //ACT
        personService.disableAccount(person1.getId());

        //ASSERT
        assertFalse(person1.isActive());
    }

    @Test
    void setPassword(){
        //ARRANGE
        String newPassword = "taaa";
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").password("araaa").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        //ACT
        personService.setPassword(person1.getId(),newPassword);

        //ASSERT
        assertEquals(newPassword,person1.getPassword());
    }

    @Test
    void getPicture() throws IOException {
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        person1.setPicture("sadasd".getBytes());

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletResponse.setContentType("image/jpeg");

        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        //ACT
        personService.getPicture(person1.getId(), mockHttpServletResponse);
    }

    @Test
    void getPersonById(){
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        Person person2 = Person.builder().id(2L).email("dasdads@gmail.com").build();
        when(personRepository.findById(person2.getId())).thenReturn(Optional.empty());

        //ACT
        Person person = personService.getPersonById(person1.getId());

        //ASSERT
        assertNotNull(person);
        assertThrows(ResponseStatusException.class,() -> personService.getPersonById(person2.getId()));
    }

    @Test
    void getPersonDetail(){
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        //ACT
        PersonDetail personDetail1 = personService.getPersonDetail(person1.getId());

        //ASSERT
        assertEquals(person1.getEmail(),personDetail1.getEmail());
    }

    @Test
    void getPersonDashBoard(){
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));
        when(dailyExpenseService.getDailyExpenseByDateForPerson(1L, LocalDate.now())).thenReturn(new DailyExpenseDetail());

        //ACT
        Dashboard dashboard = personService.getPersonDashBoard(person1.getId());

        //ASSERT
       assertNotNull(dashboard);
    }

    @Test
    void getListPerson(){
        //ARRANGE
        List<Person> persons = Arrays.asList(
                Person.builder().email("francois@gmail.com").build(),
                Person.builder().email("francois@gmail.com").build(),
                Person.builder().email("francois@gmail.com").build()
        );
        when(personRepository.findAllByActiveTrue()).thenReturn(persons);

        //ACT
        List<Person> listPersons = personService.getListPerson();

        //ASSERT
        assertEquals(3,listPersons.size());
    }
}
