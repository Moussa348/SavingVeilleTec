package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import com.keita.spendingcontrol.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public boolean createPerson(Person person) throws IOException {
        if(!personRepository.existsByEmail(person.getEmail())){
            person.setRegistrationDate(LocalDate.now());
            person.setPicture(FileUtil.setDefaultProfilePicture());
            return true;
        }
        return false;
    }

}
