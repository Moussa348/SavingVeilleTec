package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import com.keita.spendingcontrol.repository.ArticleRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Log
public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    void createArticleForDailyExperience() {
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        ArticleDetail articleDetail = new ArticleDetail(Article.builder().dailyExpense(dailyExpense).build());

        //ACT
        Article newArticle = articleService.createArticleForDailyExperience(articleDetail, dailyExpense);

        //ASSERT
        assertEquals(dailyExpense.getId(), newArticle.getDailyExpense().getId());
    }

    @Test
    void getListArticleDetailForDailyExperienceByDegreeOfUseFullness() {
        //ARRANGE
        Long id = 1L;
        Integer noPage = 0;
        DegreeOfUseFullness degreeOfUseFullness = DegreeOfUseFullness.LOW;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        List<Article> articles = Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build()
        );
        when(articleRepository.findAllByDailyExpenseIdAndDegreeOfUseFullness(id, degreeOfUseFullness, PageRequest.of(noPage,30, Sort.by("time").descending()))).thenReturn(articles);

        //ACT
        List<ArticleDetail> articleDetails = articleService.getListArticleDetailForDailyExperienceByDegreeOfUseFullness(dailyExpense.getId(), degreeOfUseFullness,noPage);

        //ASSERT
        assertEquals(3, articleDetails.size());
    }

    @Test
    void mapListArticleByDegreeOfUtility() {
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).build();
        List<Article> articles = Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.HIGH).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        );

        //ACT
        Map<DegreeOfUseFullness,Integer> mapListArticleByDegreeOfUtility = articleService.mapListArticleByDegreeOfUtility(articles);

        //ASSERT
        assertEquals(1,mapListArticleByDegreeOfUtility.get(DegreeOfUseFullness.HIGH));
        assertEquals(1,mapListArticleByDegreeOfUtility.get(DegreeOfUseFullness.MEDIUM));
        assertEquals(2,mapListArticleByDegreeOfUtility.get(DegreeOfUseFullness.LOW));

    }
}
