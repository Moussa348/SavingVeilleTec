package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import com.keita.spendingcontrol.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "http://localhost:5001")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getListArticleDetailForDailyExperienceByDegreeOfUseFullness")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public List<ArticleDetail> getListArticleDetailForDailyExperienceByDegreeOfUseFullness(
            @RequestParam("id")Long id,
            @RequestParam("degreeOfUtility")String degreeOfUtility,
            @RequestParam("noPage")Integer noPage){
        return articleService.getListArticleDetailForDailyExperienceByDegreeOfUseFullness(id, DegreeOfUseFullness.valueOf(degreeOfUtility.toUpperCase()),noPage);
    }

    @GetMapping("/getListArticleDetailForDailyExperience")
   // @PreAuthorize("@authorizationService.isConnected(#id)")
    public List<ArticleDetail> getListArticleDetailForDailyExperience(
            @RequestParam("id")Long id,
            @RequestParam("noPage")Integer noPage){
        return articleService.getListArticleDetailForDailyExperience(id,noPage);
    }

    @GetMapping("/getListArticleNameInDailyExpenseByPersonId/{id}")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public List<String> getListArticleNameInDailyExpenseByPersonId(@PathVariable Long id){
        return articleService.getListArticleNameInDailyExpenseByPersonId(id);
    }

    @PatchMapping("/increaseArticleQty/{id}")
    @PreAuthorize("@authorizationService.isConnected(#articleDetail.personId)")
    public void increaseArticleQty(@PathVariable Long id, @RequestBody ArticleDetail articleDetail){
        articleService.increaseArticleQty(id,articleDetail);
    }
}
