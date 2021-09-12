package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class DailyExpenseDetail implements Serializable {
    private Long id;
    private LocalDate date;
    private Float total;
    private List<ArticleDetail> articleDetails;
    private Map<DegreeOfUseFullness,Integer> mapArticlesUseFullness;

    public DailyExpenseDetail(){}

    public DailyExpenseDetail(DailyExpense dailyExpense, Map<DegreeOfUseFullness,Integer> mapArticlesUseFullness){
        this.id = dailyExpense.getId();
        this.date = dailyExpense.getDate();
        this.articleDetails = dailyExpense.getArticles().stream().map(ArticleDetail::new).collect(Collectors.toList());
        this.total = dailyExpense.getTotal();
        this.mapArticlesUseFullness = mapArticlesUseFullness;

    }

}
