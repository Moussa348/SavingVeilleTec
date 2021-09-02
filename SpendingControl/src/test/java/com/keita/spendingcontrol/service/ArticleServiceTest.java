package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    void createArticleForDailyExperience() {
        //ARRANGE
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).build();
        ArticleDetail articleDetail = new ArticleDetail(Article.builder().dailyExpense(dailyExpense).build());

        //ACT
        Article newArticle = articleService.createArticleForDailyExperience(articleDetail, dailyExpense);

        //ASSERT
        assertEquals(dailyExpense.getId(), newArticle.getDailyExpense().getId());
    }

    @Test
    void getListArticleDetailForDailyExpense() {
        //ARRANGE
        Long id = 1L;
        DailyExpense dailyExpense = DailyExpense.builder().id(1L).build();
        List<Article> articles = Arrays.asList(
                Article.builder().dailyExpense(dailyExpense).build(),
                Article.builder().dailyExpense(dailyExpense).build(),
                Article.builder().dailyExpense(dailyExpense).build()
        );
        when(articleRepository.findAllByDailyExpenseId(dailyExpense.getId())).thenReturn(articles);

        //ACT
        List<ArticleDetail> articleDetails = articleService.getListArticleDetailForDailyExpense(dailyExpense.getId());

        //ASSERT
        assertEquals(articles.size(), articleDetails.size());
    }
}
