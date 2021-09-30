package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.model.dto.Dashboard;
import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
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
    EmailService emailService;

    @Mock
    DailyExpenseService dailyExpenseService;

    @InjectMocks
    PersonService personService;

    @Test
    void createPerson() throws IOException, MessagingException {
        //ARRANGE
        Person person1 = Person.builder().email("araa@gmail.com").build();

        when(personRepository.existsByEmail(person1.getEmail())).thenReturn(false);
        when(personRepository.save(person1)).thenReturn(person1);

        Person person2 = Person.builder().email("dasdads@gmail.com").build();

        when(personRepository.existsByEmail(person2.getEmail())).thenReturn(true);

        doNothing().when(emailService).confirmRegistration(any(Person.class));

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
    void disableAccount() throws MessagingException {
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();

        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));
        when(personRepository.save(any(Person.class))).thenReturn(person1);
        doNothing().when(emailService).fareWellMessage(any(Person.class));

        //ACT
        personService.disableAccount(person1.getId());

        //ASSERT
        assertFalse(person1.isActive());
    }

    @Test
    void confirmVerificationCode() {
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").verificationCode("dasdadas").build();
        when(personRepository.findByVerificationCode(person1.getVerificationCode())).thenReturn(Optional.of(person1));

        Person person2 = Person.builder().id(2L).email("dasdads@gmail.com").verificationCode("23esdad").build();
        when(personRepository.findByVerificationCode(person2.getVerificationCode())).thenReturn(Optional.empty());

        //ACT
        personService.confirmVerificationCode(person1.getVerificationCode());

        //ASSERT
        assertTrue(person1.isAccountVerified());
        assertThrows(ResponseStatusException.class, () -> personService.confirmVerificationCode(person2.getVerificationCode()));
    }

    @Test
    void setPassword() {
        //ARRANGE
        String newPassword = "taaa";
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").password("araaa").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        //ACT
        personService.setPassword(person1.getId(), newPassword);

        //ASSERT
        assertEquals(newPassword, person1.getPassword());
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
    void getPersonById() {
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        Person person2 = Person.builder().id(2L).email("dasdads@gmail.com").build();
        when(personRepository.findById(person2.getId())).thenReturn(Optional.empty());

        //ACT
        Person person = personService.getPersonById(person1.getId());

        //ASSERT
        assertNotNull(person);
        assertThrows(ResponseStatusException.class, () -> personService.getPersonById(person2.getId()));
    }

    @Test
    void getPersonDetail() {
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").build();
        when(personRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        //ACT
        PersonDetail personDetail1 = personService.getPersonDetail(person1.getId());

        //ASSERT
        assertEquals(person1.getEmail(), personDetail1.getEmail());
    }

    @Test
    void getPersonDashBoard() {
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
    void getListPerson() {
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
        assertEquals(3, listPersons.size());
    }

    @Test
    void findPersonByEmailAndPassword() {
        //ARRANGE
        String email2 = "adadasdasd";
        String password2 = "adadasdasd";
        when(personRepository.findByEmailAndPasswordAndActiveTrueAndAccountVerifiedTrue(email2, password2)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(ResponseStatusException.class, () -> personService.findPersonByEmailAndPassword(email2, password2, HttpStatus.NOT_FOUND));
    }

    @Test
    void deleteAllUnverifiedAccount() {
        //ARRANGE
        List<Person> persons = Arrays.asList(
                Person.builder().build(),
                Person.builder().build(),
                Person.builder().build()
        );
        when(personRepository.findAllByAccountVerifiedFalse()).thenReturn(persons);

        //ACT
        Integer nbrOfUnVerifiedAccountDeleted = personService.deleteAllUnverifiedAccount();

        //ASSERT
        assertEquals(persons.size(), nbrOfUnVerifiedAccountDeleted);
    }
}
