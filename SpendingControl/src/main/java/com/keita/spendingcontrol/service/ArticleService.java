package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import com.keita.spendingcontrol.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article createArticleForDailyExperience(ArticleDetail articleDetail, DailyExpense dailyExpense){
         return new Article(articleDetail,dailyExpense);
    }

    public List<ArticleDetail> getListArticleDetailForDailyExperienceByDegreeOfUtility(Long id, DegreeOfUtility degreeOfUtility,Integer noPage){
        return articleRepository.findAllByDailyExpenseIdAndDegreeOfUtility(id,degreeOfUtility, PageRequest.of(noPage,30, Sort.by("time").descending()))
                .stream()
                .map(ArticleDetail::new).collect(Collectors.toList());
    }

    public Map<DegreeOfUtility,Integer> mapListArticleByDegreeOfUtility(List<Article> articles){
        Map<DegreeOfUtility,Integer> mapArticleUtility = new HashMap<>();

        mapArticleUtility.put(DegreeOfUtility.LOW,0);
        mapArticleUtility.put(DegreeOfUtility.MEDIUM,0);
        mapArticleUtility.put(DegreeOfUtility.HIGH,0);

        articles.forEach(article -> mapArticleUtility.computeIfPresent(article.getDegreeOfUtility(),(k,v) -> v+1));

        return mapArticleUtility;
    }
}
