package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.Person;
import lombok.Data;

import java.io.Serializable;

@Data
public class Dashboard implements Serializable {
    private PersonDetail personDetail;
    private DailyExpenseDetail dailyExpenseDetail;

    public Dashboard(Person person, DailyExpenseDetail dailyExpenseDetail){
        this.personDetail = new PersonDetail(person);
        this.dailyExpenseDetail = dailyExpenseDetail;
    }
}
