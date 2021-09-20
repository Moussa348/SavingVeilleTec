package com.keita.spendingcontrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtService jwtService;


    @Test
    void createPerson() throws Exception{
        //ARRANGE
        Person person1 = Person.builder().firstName("asdasdas").lastName("dsadasd").email("developpeurspring@gmail.com").password("reda123").build();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/person/createPerson")
                .content(mapper.writeValueAsString(person1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertEquals("false",mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void setPicture() throws Exception{
        //ARRANGE
        Long id = 1L;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        MockMultipartHttpServletRequestBuilder builder1 =
                MockMvcRequestBuilders.multipart("/person/setPicture/");
        builder1.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        MockMultipartHttpServletRequestBuilder builder2 =
                MockMvcRequestBuilders.multipart("/person/setPicture/");
        builder2.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(builder1
                .file(new MockMultipartFile("multipartFile", "file.jpg", "image/jpeg", "taaa".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token1)
                .param("id",id.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(builder2
                .file(new MockMultipartFile("multipartFile", "file.jpg", "image/jpeg", "taaa".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token2)
                .param("id",id.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN,mvcResult2.getResponse().getStatus());
    }

    @Test
    void setPassword() throws Exception{
        //ARRANGE
        Long id = 1L;
        String newPassword = "araaaaa";

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/setPassword")
                .header(HttpHeaders.AUTHORIZATION, token1)
                .param("id",id.toString())
                .param("password",newPassword)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/setPassword")
                .header(HttpHeaders.AUTHORIZATION, token2)
                .param("id",id.toString())
                .param("password",newPassword)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN,mvcResult2.getResponse().getStatus());
    }

    @Test
    void disableAccount() throws Exception{
        //ARRANGE
        Long id = 1L;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/disableAccount/" + id)
                .header(HttpHeaders.AUTHORIZATION, token1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/disableAccount/" + id)
                .header(HttpHeaders.AUTHORIZATION, token2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN,mvcResult2.getResponse().getStatus());
    }

    @Test
    void confirmVerificationCode() throws Exception{
        //ARRANGE
        String verificationCode = "adsadadasd";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/confirmVerificationCode/" + verificationCode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_NOT_FOUND,mvcResult1.getResponse().getStatus());
    }

    @Test
    void getPicture() throws Exception{
        //ARRANGE
        Long id = 1L;

        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPicture/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
    }


    @Test
    void getPersonDetail() throws Exception{
        //ARRANGE
        Long id = 1L;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPersonDetail/" + id)
                .header(HttpHeaders.AUTHORIZATION, token1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPersonDetail/" + id)
                .header(HttpHeaders.AUTHORIZATION, token2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN,mvcResult2.getResponse().getStatus());
    }

    @Test
    void getPersonDashBoard() throws Exception{
        //ARRANGE
        Long id = 1L;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPersonDashBoard/" + id)
                .header(HttpHeaders.AUTHORIZATION, token1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPersonDashBoard/" + id)
                .header(HttpHeaders.AUTHORIZATION, token2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN,mvcResult2.getResponse().getStatus());
    }
}
