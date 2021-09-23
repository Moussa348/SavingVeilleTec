package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailyAnalyticService {


    public ArticleDetail getMostExpensiveArticle(List<Article> articles) {
        return articles.size() > 0 ? articles.stream().max(Comparator.comparing(Article::getPrice)).map(ArticleDetail::new).get():new ArticleDetail();
    }

    public Map<DegreeOfUseFullness, ArticleDetail> getMapMostExpensiveArticlesByUseFullness(List<Article> articles) {
        Map<DegreeOfUseFullness, ArticleDetail> mostExpensiveArticlesByUseFullness = new HashMap<>();

        mostExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.LOW, getMostExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.LOW)).collect(Collectors.toList())));
        mostExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.MEDIUM, getMostExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.MEDIUM)).collect(Collectors.toList())));
        mostExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.HIGH, getMostExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.HIGH)).collect(Collectors.toList())));

        return mostExpensiveArticlesByUseFullness;
    }

    public ArticleDetail getLessExpensiveArticle(List<Article> articles) {
        return articles.size() > 0 ? articles.stream().min(Comparator.comparing(Article::getPrice)).map(ArticleDetail::new).get(): new ArticleDetail();
    }

    public Map<DegreeOfUseFullness, ArticleDetail> getMapLessExpensiveArticlesByUseFullness(List<Article> articles) {
        Map<DegreeOfUseFullness, ArticleDetail> lessExpensiveArticlesByUseFullness = new HashMap<>();

        lessExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.LOW, getLessExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.LOW)).collect(Collectors.toList())));
        lessExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.MEDIUM, getLessExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.MEDIUM)).collect(Collectors.toList())));
        lessExpensiveArticlesByUseFullness.put(DegreeOfUseFullness.HIGH, getLessExpensiveArticle(articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.HIGH)).collect(Collectors.toList())));

        return lessExpensiveArticlesByUseFullness;
    }

    public Map<DegreeOfUseFullness, Float> getMapTotalByUseFullness(List<Article> articles) {
        Map<DegreeOfUseFullness, Float> totalByUseFullness = new HashMap<>();

        totalByUseFullness.put(DegreeOfUseFullness.LOW, articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.LOW)).map(Article::getPrice).reduce(0.0f, Float::sum));
        totalByUseFullness.put(DegreeOfUseFullness.MEDIUM, articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.MEDIUM)).map(Article::getPrice).reduce(0.0f, Float::sum));
        totalByUseFullness.put(DegreeOfUseFullness.HIGH, articles.stream().filter(article -> article.getDegreeOfUseFullness().equals(DegreeOfUseFullness.HIGH)).map(Article::getPrice).reduce(0.0f, Float::sum));

        return totalByUseFullness;
    }

}
