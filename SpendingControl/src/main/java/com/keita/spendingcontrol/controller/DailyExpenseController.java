package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.service.DailyExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dailyExpense")
public class DailyExpenseController {

    @Autowired
    private DailyExpenseService dailyExpenseService;

    @PatchMapping("/addArticleToDailyExpense")
    public void addArticleToDailyExpense(@RequestBody ArticleDetail articleDetail){
        dailyExpenseService.addArticleToDailyExpense(articleDetail);
    }

    @GetMapping("/getDailyExpenseByDateForPerson")
    public DailyExpenseDetail getDailyExpenseByDateForPerson(@RequestParam("id")Long id, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date){
        return dailyExpenseService.getDailyExpenseByDateForPerson(id,date);
    }
}