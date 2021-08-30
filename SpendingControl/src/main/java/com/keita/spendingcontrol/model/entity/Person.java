package com.keita.spendingcontrol.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] picture;
    private LocalDate registrationDate;
    private String firstName,lastName,email,password,roles;
    private boolean active;

    public Person(){}

    @Builder
    public Person(Long id, byte[] picture, String firstName, String lastName, String email, String password, String roles) {
        this.id = id;
        this.picture = picture;
        this.registrationDate = LocalDate.now();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.active = false;
    }
}
