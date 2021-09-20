package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DailyAnalyticService {



    public ArticleDetail getMostExpensiveArticle(List<Article> articles) {
        return articles.stream().max(Comparator.comparing(Article::getPrice)).map(ArticleDetail::new).get();
    }

    public Map<DegreeOfUseFullness, ArticleDetail> getMapMostExpensiveArticlesByUseFullness(List<Article> articles) {
        return null;
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
