package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import com.keita.spendingcontrol.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "http://localhost:5001")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getListArticleDetailForDailyExperienceByDegreeOfUtility")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public List<ArticleDetail> getListArticleDetailForDailyExperienceByDegreeOfUtility(
            @RequestParam("id")Long id,
            @RequestParam("degreeOfUtility")String degreeOfUtility,
            @RequestParam("noPage")Integer noPage){
        return articleService.getListArticleDetailForDailyExperienceByDegreeOfUtility(id, DegreeOfUtility.valueOf(degreeOfUtility.toUpperCase()),noPage);
    }
}
