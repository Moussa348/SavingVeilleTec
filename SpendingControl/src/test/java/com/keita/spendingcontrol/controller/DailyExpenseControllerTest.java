package com.keita.spendingcontrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Log
public class DailyExpenseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void addArticleToDailyExpense () throws Exception{
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        ArticleDetail articleDetail = new ArticleDetail(Article.builder().dailyExpense(dailyExpense).build());

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/dailyExpense/addArticleToDailyExpense")
                //.header(HttpHeaders.AUTHORIZATION, token1)
                .content(mapper.writeValueAsString(articleDetail))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        /*
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.patch("/dailyExpense/addArticleToDailyExpense")
                //.header(HttpHeaders.AUTHORIZATION, token2)
                .content(mapper.writeValueAsString(articleDetail))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
         */

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK, mvcResult1.getResponse().getStatus());
        //assertEquals(MockHttpServletResponse.SC_OK, mvcResult2.getResponse().getStatus());
    }

    @Test
    void getDailyExpenseByDateForPerson () throws Exception{
        //ARRANGE
        Long id = 1L;
        LocalDate date = LocalDate.now();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/dailyExpense/getDailyExpenseByDateForPerson")
                //.header(HttpHeaders.AUTHORIZATION, token1)
                .param("id",id.toString())
                .param("date",date.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        /*
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/dailyExpense/getDailyExpenseByDateForPerson")
                //.header(HttpHeaders.AUTHORIZATION, token2)
                .param("id",id.toString())
                .param("date",date.toString())
                .content(mapper.writeValueAsString(articleDetail))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
         */

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK, mvcResult1.getResponse().getStatus());
        //assertEquals(MockHttpServletResponse.SC_OK, mvcResult2.getResponse().getStatus());
    }
}
