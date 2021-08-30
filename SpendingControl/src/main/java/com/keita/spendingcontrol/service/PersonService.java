package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import com.keita.spendingcontrol.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        return new PersonDetail(findPersonById(id));
    }

    public void getPicture(Long id, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("image/jpeg");

        InputStream inputStream = new ByteArrayInputStream(findPersonById(id).getPicture());

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

    private Person findPersonById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find person with id: " + id));
    }


}