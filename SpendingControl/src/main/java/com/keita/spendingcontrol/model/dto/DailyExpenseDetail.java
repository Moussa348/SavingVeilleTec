package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class DailyExpenseDetail implements Serializable {
    private Long id;
    private LocalDate date;
    private Float total;
    private Map<DegreeOfUtility,Integer> mapArticleUtility;

    public DailyExpenseDetail(){}

    public DailyExpenseDetail(DailyExpense dailyExpense, Map<DegreeOfUtility,Integer> mapArticleUtility){
        this.id = dailyExpense.getId();
        this.date = dailyExpense.getDate();
        this.total = dailyExpense.getTotal();
        this.mapArticleUtility = mapArticleUtility;

    }

}
