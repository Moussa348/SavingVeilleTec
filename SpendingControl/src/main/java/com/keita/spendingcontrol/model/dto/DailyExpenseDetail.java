package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

@Data
public class DailyExpenseDetail implements Serializable {
    private Long id;
    private LocalDate date;
    private Float total;
    private Map<DegreeOfUseFullness,Integer> mapArticlesUseFullness;

    public DailyExpenseDetail(){}

    public DailyExpenseDetail(DailyExpense dailyExpense, Map<DegreeOfUseFullness,Integer> mapArticlesUseFullness){
        this.id = dailyExpense.getId();
        this.date = dailyExpense.getDate();
        this.total = dailyExpense.getTotal();
        this.mapArticlesUseFullness = mapArticlesUseFullness;

    }

}
