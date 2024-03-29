package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.dto.DailyAnalytic;
import com.keita.spendingcontrol.model.dto.DailyExpenseDetail;
import com.keita.spendingcontrol.service.DailyExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dailyExpense")
@CrossOrigin(origins = {"http://localhost:5001","http://192.168.0.184:5001"})
public class DailyExpenseController {

    private final DailyExpenseService dailyExpenseService;

    public DailyExpenseController(DailyExpenseService dailyExpenseService) {
        this.dailyExpenseService = dailyExpenseService;
    }

    @PatchMapping("/addArticleToDailyExpense")
    @PreAuthorize("@authorizationService.isConnected(#articleDetail.personId)")
    public void addArticleToDailyExpense(@RequestBody ArticleDetail articleDetail) {
        dailyExpenseService.addArticleToDailyExpense(articleDetail);
    }

    @GetMapping("/getDailyExpenseByDateForPerson")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public DailyExpenseDetail getDailyExpenseByDateForPerson(@RequestParam("id") Long id, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return dailyExpenseService.getDailyExpenseByDateForPerson(id, date);
    }

    @GetMapping("/getDailyAnalytic")
    @PreAuthorize("@authorizationService.isConnected(#id)")
    public DailyAnalytic getDailyAnalytic(@RequestParam("id") Long id, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return dailyExpenseService.getDailyAnalytic(id, date);
    }

}
