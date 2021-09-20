package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.entity.DailyExpense;
import org.springframework.stereotype.Service;

@Service
public class DailyAnalyticService {


    public Float getTotalPrice(DailyExpense dailyExpense){
        return dailyExpense.getArticles().stream().map(Article::getPrice).reduce(0.0f,Float::sum);
    }

}
