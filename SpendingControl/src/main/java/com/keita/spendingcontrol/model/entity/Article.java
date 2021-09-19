package com.keita.spendingcontrol.model.entity;

import com.keita.spendingcontrol.model.dto.ArticleDetail;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String name;
    private Integer qty;
    private Float price;
    private DegreeOfUseFullness degreeOfUseFullness;

    @ManyToOne
    private DailyExpense dailyExpense;

    public Article(){}

    @Builder
    public Article(Long id, LocalDateTime time, String name, Integer qty, Float price, DegreeOfUseFullness degreeOfUseFullness, DailyExpense dailyExpense) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.degreeOfUseFullness = degreeOfUseFullness;
        this.dailyExpense = dailyExpense;
    }

    public Article(ArticleDetail articleDetail, DailyExpense dailyExpense){
        this.time = LocalDateTime.now();
        this.name = articleDetail.getName();
        this.qty = articleDetail.getQty();
        this.price = articleDetail.getPrice();
        this.degreeOfUseFullness = DegreeOfUseFullness.valueOf(articleDetail.getDegreeOfUseFullness().toUpperCase());
        this.dailyExpense = dailyExpense;
    }
}
