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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Log
@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @Mock
    DailyExpenseService dailyExpenseService;

    @InjectMocks
    ArticleService articleService;

    @Test
    void createArticleForDailyExpense() {
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        ArticleDetail articleDetail = new ArticleDetail(Article.builder().dailyExpense(dailyExpense).build());

        //ACT
        Article newArticle = articleService.createArticleForDailyExpense(articleDetail, dailyExpense);

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
    void getListArticleDetailForDailyExperience() {
        //ARRANGE
        Long id = 1L;
        Integer noPage = 0;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        List<Article> articles = Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build()
        );
        when(articleRepository.findAllByDailyExpenseId(id, PageRequest.of(noPage,10, Sort.by("time").descending()))).thenReturn(articles);

        //ACT
        List<ArticleDetail> articleDetails = articleService.getListArticleDetailForDailyExperience(dailyExpense.getId(),noPage);

        //ASSERT
        assertEquals(3, articleDetails.size());
    }

    @Test
    void getListArticleNameInDailyExpenseByPersonId() {
        //ARRANGE
        Long id = 1L;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        dailyExpense.setArticles(Arrays.asList(Article.builder().name("cereales").build(),Article.builder().name("magnoc").build()));

        when(dailyExpenseService.findDailyExpenseByPersonIdAndDate(id, LocalDate.now())).thenReturn(dailyExpense);

        //ACT
        List<String> articleNames = articleService.getListArticleNameInDailyExpenseByPersonId(id);

        //ASSERT
        assertEquals(2, articleNames.size());
    }

    @Test
    void increaseArticleQty(){
        //ARRANGE
        Long id = 1L;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).person(Person.builder().id(1L).build()).build();
        Article article = Article.builder().id(1L).qty(0).dailyExpense(dailyExpense).price(0.0f).name("cereales").degreeOfUseFullness(DegreeOfUseFullness.LOW).build();
        ArticleDetail articleDetail =  new ArticleDetail(Article.builder().id(1L).name("cereales").dailyExpense(dailyExpense).qty(4).price(24.5f).degreeOfUseFullness(DegreeOfUseFullness.LOW).build());

        when(articleRepository.findByNameAndDailyExpenseId(articleDetail.getName(),id)).thenReturn(Optional.of(article));

        //ACT
        articleService.increaseArticleQty(id,articleDetail);

        //ASSERT
        assertEquals(articleDetail.getQty(),article.getQty());
        assertEquals(articleDetail.getPrice(),article.getPrice());
    }

    @Test
    void mapListArticleByDegreeOfUseFullness() {
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).build();
        List<Article> articles = Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.HIGH).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.LOW).build(),
                Article.builder().dailyExpense(dailyExpense).degreeOfUseFullness(DegreeOfUseFullness.MEDIUM).build()
        );

        //ACT
        Map<DegreeOfUseFullness,Integer> mapListArticleByDegreeOfUtility = articleService.mapListArticleByDegreeOfUseFullness(articles);

        //ASSERT
        assertEquals(1,mapListArticleByDegreeOfUtility.get(DegreeOfUseFullness.HIGH));
        assertEquals(1,mapListArticleByDegreeOfUtility.get(DegreeOfUseFullness.MEDIUM));
        assertEquals(2,mapListArticleByDegreeOfUtility.get(DegreeOfUseFullness.LOW));

    }
}
