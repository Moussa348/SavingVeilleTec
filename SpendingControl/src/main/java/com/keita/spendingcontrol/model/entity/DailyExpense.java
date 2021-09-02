package com.keita.spendingcontrol.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class DailyExpense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Float total;

    @OneToMany(mappedBy = "dailyExpense")
    private List<Article> articles;

    public DailyExpense(){}

    @Builder
    public DailyExpense(Long id, LocalDate date, Float total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }
}
