package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article createArticle(ArticleDetail articleDetail, DailyExpense dailyExpense){
         return new Article(articleDetail,dailyExpense);
    }

    public List<ArticleDetail> getListArticleDetailForDailyExpense(Long id){
        return articleRepository.findAllByDailyExpenseId(id).stream().map(ArticleDetail::new).collect(Collectors.toList());
    }
}
