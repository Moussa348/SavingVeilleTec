package com.keita.spendingcontrol.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String name;
    private Integer qty;
    private Float price;

    @ManyToOne
    private DailyExpense dailyExpense;

    public Article(){}

    @Builder
    public Article(Long id, LocalDateTime time, String name, Integer qty, Float price, DailyExpense dailyExpense) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.dailyExpense = dailyExpense;
    }
}
