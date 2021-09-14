package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.Dashboard;
import com.keita.spendingcontrol.model.dto.PersonDetail;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.repository.PersonRepository;
import com.keita.spendingcontrol.util.FileUtil;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DailyExpenseService dailyExpenseService;

    @Autowired
    private EmailService emailService;

    public boolean createPerson(Person person) throws IOException, MessagingException {
        if(!personRepository.existsByEmail(person.getEmail())){
            person.setRegistrationDate(LocalDate.now());
            person.setRoles("USER");
            person.setPicture(FileUtil.setDefaultProfilePicture());
            person.setVerificationCode(RandomString.make(20));
            emailService.confirmRegistration(personRepository.save(person));
            return true;
        }
        return false;
    }

    public void setPicture(Long id, MultipartFile multipartFile) throws IOException {
        Person person = getPersonById(id);
        //TODO --> check if multipartFile is really an image or else throw IOException
        if (FileUtil.isFileAnImage(Objects.requireNonNull(multipartFile.getOriginalFilename()))) {

            person.setPicture(multipartFile.getBytes());

            personRepository.save(person);
        }else
            throw new IOException("This file is not an image");
    }

    //TODO --> send encryptedPassword
    public void setPassword(Long id, String password){
        Person person = getPersonById(id);

        person.setPassword(password);

        personRepository.save(person);
    }

    public void disableAccount(Long id){
        Person person = getPersonById(id);

        person.setActive(false);

        personRepository.save(person);
    }

    public void confirmVerificationCode(String verificationCode){
        Person person = personRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't find person with this verification code"));

        person.setAccountVerified(true);
        person.setVerificationCode("");

        dailyExpenseService.createDailExpenseForPerson(personRepository.save(person));
    }

    //TODO delete all account unverified


    public void getPicture(Long id, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("image/jpeg");

        InputStream inputStream = new ByteArrayInputStream(getPersonById(id).getPicture());

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

    public Person getPersonById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find person with id: " + id));
    }


    public PersonDetail getPersonDetail(Long id){
        return new PersonDetail(getPersonById(id));
    }

    //TODO --> ajouter list d'articles avec le nom,quantité et le total
    public Dashboard getPersonDashBoard(Long id){
        return new Dashboard(getPersonById(id),dailyExpenseService.getDailyExpenseByDateForPerson(id,LocalDate.now()));
    }

    public List<Person> getListPerson(){
        return personRepository.findAllByActiveTrue();
    }

    public Person findPersonByEmailAndPassword(String email,String password){
        return personRepository.findByEmailAndPassword(email,password)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't find person with : " + email));
    }
}
