package com.keita.spendingcontrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import com.keita.spendingcontrol.security.JwtService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Log
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtService jwtService;

    @Test
    void getListArticleDetailForDailyExperienceByDegreeOfUseFullness() throws Exception{
        //ARRANGE
        Long id = 1L;
        DegreeOfUseFullness degreeOfUseFullness = DegreeOfUseFullness.LOW;
        Integer noPage = 0;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/article/getListArticleDetailForDailyExperienceByDegreeOfUseFullness")
                .header(HttpHeaders.AUTHORIZATION, token1)
                .param("id",id.toString())
                .param("degreeOfUtility", degreeOfUseFullness.toString())
                .param("noPage",noPage.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/article/getListArticleDetailForDailyExperienceByDegreeOfUseFullness")
                .header(HttpHeaders.AUTHORIZATION, token2)
                .param("id",id.toString())
                .param("degreeOfUtility", degreeOfUseFullness.toString())
                .param("noPage",noPage.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK, mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN, mvcResult2.getResponse().getStatus());
    }


    @Test
    void getListArticleDetailForDailyExperience() throws Exception{
        //ARRANGE
        Long id = 1L;
        Integer noPage = 0;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/article/getListArticleDetailForDailyExperience")
                .header(HttpHeaders.AUTHORIZATION, token1)
                .param("id",id.toString())
                .param("noPage",noPage.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/article/getListArticleDetailForDailyExperience")
                .header(HttpHeaders.AUTHORIZATION, token2)
                .param("id",id.toString())
                .param("noPage",noPage.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK, mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN, mvcResult2.getResponse().getStatus());
    }


    @Test
    void getListArticleNameInDailyExpenseByPersonId() throws Exception{
        //ARRANGE
        Long id = 1L;
        Integer noPage = 0;

        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/article/getListArticleNameInDailyExpenseByPersonId/" + id)
                .header(HttpHeaders.AUTHORIZATION, token1)
                .param("id",id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/article/getListArticleNameInDailyExpenseByPersonId/" + id)
                .header(HttpHeaders.AUTHORIZATION, token2)
                .param("id",id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK, mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN, mvcResult2.getResponse().getStatus());
    }

    @Test
    void increaseArticleQty() throws Exception{
        //ARRANGE
        Long id = 1L;
        Person person1 = Person.builder().id(1L).roles("USER").build();
        String token1 = "Bearer " + jwtService.generate(person1);

        Person person2 = Person.builder().id(2L).roles("USER").build();
        String token2 = "Bearer " + jwtService.generate(person2);

        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(person1).build();
        ArticleDetail articleDetail =  new ArticleDetail(Article.builder().id(1L).name("cereales").dailyExpense(dailyExpense).qty(4).price(24.5f).build());

        //ACT

        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/article/increaseArticleQty/" + id)
                .header(HttpHeaders.AUTHORIZATION, token1)
                .content(mapper.writeValueAsString(articleDetail))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.patch("/article/increaseArticleQty/" + id)
                .header(HttpHeaders.AUTHORIZATION, token2)
                .content(mapper.writeValueAsString(articleDetail))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        //ASSERT
        assertEquals(MockHttpServletResponse.SC_OK,mvcResult1.getResponse().getStatus());
        assertEquals(MockHttpServletResponse.SC_FORBIDDEN,mvcResult2.getResponse().getStatus());
    }
}
