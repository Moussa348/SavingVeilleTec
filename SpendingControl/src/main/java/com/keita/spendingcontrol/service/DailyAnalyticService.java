package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyAnalyticService {



    public ArticleDetail getMostExpensiveArticle(List<Article> articles) {
        return articles.stream().max(Comparator.comparing(Article::getPrice)).map(ArticleDetail::new).get();
    }

    public Map<DegreeOfUseFullness, ArticleDetail> getMapMostExpensiveArticlesByUseFullness(List<Article> articles) {
        Map<DegreeOfUseFullness,ArticleDetail> mostExpensiveArticlesByUseFullness = new HashMap<>();

        mostExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.LOW,getMostExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.LOW)).collect(Collectors.toList())));
        mostExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.MEDIUM,getMostExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.MEDIUM)).collect(Collectors.toList())));
        mostExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.HIGH,getMostExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.HIGH)).collect(Collectors.toList())));

        return mostExpensiveArticlesByUseFullness;
    }

    public ArticleDetail getLessExpensiveArticle(List<Article> articles) {
        return articles.stream().min(Comparator.comparing(Article::getPrice)).map(ArticleDetail::new).get();
    }

    public Map<DegreeOfUseFullness, ArticleDetail> getMapLessExpensiveArticlesByUseFullness(List<Article> articles) {

        return null;
    }

    public Map<DegreeOfUseFullness, Float> getMapTotalByUseFullness(List<Article> articles) {
        return null;
    }

}
