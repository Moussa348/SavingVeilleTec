package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import com.keita.spendingcontrol.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public PersonDetail getPersonDetail(Long id){
        return personRepository.findById(id)
                .map(PersonDetail::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find person with id: " + id));
    }

}
