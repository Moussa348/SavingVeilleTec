package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import com.keita.spendingcontrol.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article createArticleForDailyExpense(ArticleDetail articleDetail, DailyExpense dailyExpense){
         return new Article(articleDetail,dailyExpense);
    }

    public List<ArticleDetail> getListArticleDetailForDailyExperienceByDegreeOfUseFullness(Long id, DegreeOfUseFullness degreeOfUseFullness, Integer noPage){
        return articleRepository.findAllByDailyExpenseIdAndDegreeOfUseFullness(id, degreeOfUseFullness, PageRequest.of(noPage,30, Sort.by("time").descending()))
                .stream()
                .map(ArticleDetail::new).collect(Collectors.toList());
    }

    public List<ArticleDetail> getListArticleDetailForDailyExperience(Long id,Integer noPage){
        return articleRepository.findAllByDailyExpenseId(id,PageRequest.of(noPage,10,Sort.by("time").descending()))
                .stream().map(ArticleDetail::new).collect(Collectors.toList());
    }

    public void increaseArticleQty(Long id,ArticleDetail articleDetail){
        Article article = articleRepository.findByNameAndDailyExpenseId(articleDetail.getName(),id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't find article with name : " + articleDetail.getName()));

        article.setQty(article.getQty() + articleDetail.getQty());
        article.setPrice(article.getPrice() + articleDetail.getPrice());

        articleRepository.save(article);
    }

    public Map<DegreeOfUseFullness,Integer> mapListArticleByDegreeOfUseFullness(List<Article> articles){
        Map<DegreeOfUseFullness,Integer> mapArticleUtility = new HashMap<>();

        mapArticleUtility.put(DegreeOfUseFullness.LOW,0);
        mapArticleUtility.put(DegreeOfUseFullness.MEDIUM,0);
        mapArticleUtility.put(DegreeOfUseFullness.HIGH,0);

        articles.forEach(article -> mapArticleUtility.computeIfPresent(article.getDegreeOfUseFullness(),(k, v) -> v+1));

        return mapArticleUtility;
    }
}
