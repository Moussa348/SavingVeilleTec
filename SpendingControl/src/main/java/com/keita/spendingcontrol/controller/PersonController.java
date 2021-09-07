package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/createPerson")
    public boolean createPerson(@RequestBody Person person) throws IOException {
        return personService.createPerson(person);
    }

    @PatchMapping("/setPicture")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void setPicture(@RequestParam("id") Long id, @RequestParam("multipartFile")MultipartFile multipartFile) throws IOException {
         personService.setPicture(id,multipartFile);
    }

    @PatchMapping("/setPassword")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void setPassword(@RequestParam("id") Long id, @RequestParam("password")String password) {
        personService.setPassword(id,password);
    }

    @PatchMapping("/disableAccount/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void disableAccount(@PathVariable Long id) {
        personService.disableAccount(id);
    }

    @GetMapping("/getPicture/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void getPicture(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        personService.getPicture(id,httpServletResponse);
    }

    @GetMapping("/getPersonDetail/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public PersonDetail getPersonDetail(@PathVariable Long id) {
        return personService.getPersonDetail(id);
    }
}
