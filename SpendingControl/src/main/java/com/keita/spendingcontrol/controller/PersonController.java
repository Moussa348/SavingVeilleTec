package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.Dashboard;
import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.service.PersonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = {"http://localhost:5001","http://192.168.0.184:5001"})
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/createPerson")
    public boolean createPerson(@RequestBody Person person) throws IOException, MessagingException {
        return personService.createPerson(person);
    }

    @PatchMapping("/setPicture")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void setPicture(@RequestParam("id") Long id, @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        personService.setPicture(id, multipartFile);
    }

    @PatchMapping("/setPasswordWithId")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void setPassword(@RequestParam("id") Long id, @RequestParam("password") String password) {
        personService.setPassword(id, password);
    }

    @PatchMapping("/setPasswordWithEmail")
    public void setPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
        personService.setPassword(email, password);
    }

    @PatchMapping("/disableAccount/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public void disableAccount(@PathVariable Long id) throws MessagingException {
        personService.disableAccount(id);
    }

    @PatchMapping("/confirmVerificationCode/{verificationCode}")
    public void confirmVerificationCode(@PathVariable String verificationCode) {
         personService.confirmVerificationCode(verificationCode);
    }

    @GetMapping("/getPicture/{id}")
    public void getPicture(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        personService.getPicture(id, httpServletResponse);
    }

    @GetMapping("/getPersonDetail/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public PersonDetail getPersonDetail(@PathVariable Long id) {
        return personService.getPersonDetail(id);
    }

    @GetMapping("/getPersonDashBoard/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public Dashboard getPersonDashBoard(@PathVariable Long id) {
        return personService.getPersonDashBoard(id);
    }
}
