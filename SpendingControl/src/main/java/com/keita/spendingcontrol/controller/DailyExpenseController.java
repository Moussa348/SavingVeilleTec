package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.repository.DailyExpenseRepository;
import com.keita.spendingcontrol.service.DailyExpenseService;
import net.bytebuddy.utility.RandomString;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailyExpense")
@CrossOrigin(origins = "http://localhost:5001")
public class DailyExpenseController {

    @Autowired
    private DailyExpenseService dailyExpenseService;

    @PatchMapping("/addArticleToDailyExpense")
    @PreAuthorize("@authorizationService.isConnected(#articleDetail.personId)")
    public void addArticleToDailyExpense(@RequestBody ArticleDetail articleDetail){
        dailyExpenseService.addArticleToDailyExpense(articleDetail);
    }

    @GetMapping("/getDailyExpenseByDateForPerson")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public DailyExpenseDetail getDailyExpenseByDateForPerson(@RequestParam("id")Long id, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date){
        return dailyExpenseService.getDailyExpenseByDateForPerson(id,date);
    }

}
