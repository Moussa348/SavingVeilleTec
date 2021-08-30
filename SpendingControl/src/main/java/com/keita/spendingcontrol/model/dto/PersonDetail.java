package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.Person;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PersonDetail implements Serializable {
    private Long id;
    private LocalDate registrationDate;
    private String firstName,lastName,email;

    public PersonDetail(){}

    public PersonDetail(Person person){
        this.id = person.getId();
        this.registrationDate = person.getRegistrationDate();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
    }
}
