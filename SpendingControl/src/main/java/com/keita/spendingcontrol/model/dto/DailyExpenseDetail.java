package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DailyExpenseDetail implements Serializable {
    private Long id;
    private LocalDate date;
    private Float total;
    private Map<DegreeOfUtility,Integer> mapArticlesUtility;

    public DailyExpenseDetail(){}

    public DailyExpenseDetail(DailyExpense dailyExpense, Map<DegreeOfUtility,Integer> mapArticlesUtility){
        this.id = dailyExpense.getId();
        this.date = dailyExpense.getDate();
        this.total = dailyExpense.getTotal();
        this.mapArticlesUtility = mapArticlesUtility;

    }

}
