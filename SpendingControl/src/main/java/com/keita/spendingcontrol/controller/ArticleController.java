package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import com.keita.spendingcontrol.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getListArticleDetailForDailyExperienceByDegreeOfUtility")
    public List<ArticleDetail> getListArticleDetailForDailyExperienceByDegreeOfUtility(
            @RequestParam("id")Long id,
            @RequestParam("degreeOfUtility")String degreeOfUtility,
            @RequestParam("noPage")Integer noPage){
        return articleService.getListArticleDetailForDailyExperienceByDegreeOfUtility(id, DegreeOfUtility.valueOf(degreeOfUtility.toUpperCase()),noPage);
    }
}
