package com.keita.spendingcontrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.spendingcontrol.model.entity.Person;
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


    @Test
    void createPerson() throws Exception{
        //ARRANGE
        Person person1 = Person.builder().email("asdadasdasd@gmail.com").password("reda123").build();
        Person person2 = Person.builder().email("redaHamza@gmail.com").password("reda123").build();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/person/createPerson")
                .content(mapper.writeValueAsString(person1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/person/createPerson")
                .content(mapper.writeValueAsString(person2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("true",mvcResult1.getResponse().getContentAsString());
        assertEquals("false",mvcResult2.getResponse().getContentAsString());

    }

    @Test
    void setPicture() throws Exception{
        //ARRANGE
        Long id = 1L;
        String newPassword = "araaaaa";

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/person/setPicture/");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(builder
                .file(new MockMultipartFile("multipartFile", "file.jpg", "image/jpeg", "taaa".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .param("id",id.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
    }

    @Test
    void setPassword() throws Exception{
        //ARRANGE
        Long id = 1L;
        String newPassword = "araaaaa";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/setPassword")
                .param("id",id.toString())
                .param("password",newPassword)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());

    }

    @Test
    void disableAccount() throws Exception{
        //ARRANGE
        Long id = 1L;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/person/disableAccount/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
    }

    @Test
    void getPicture() throws Exception{
        //ARRANGE
        Long id = 1L;

        //ACT
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
        Long id2 = 100L;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPersonDetail/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/person/getPersonDetail/" + id2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_BAD_REQUEST,mvcResult2.getResponse().getStatus());

    }
}
