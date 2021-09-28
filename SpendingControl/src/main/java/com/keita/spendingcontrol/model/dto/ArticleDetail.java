package com.keita.spendingcontrol.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keita.spendingcontrol.model.entity.Article;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleDetail implements Serializable {
    private Long id,personId,dailyId;

    private LocalDateTime time;
    private String name;
    private Integer qty;
    private Float price;
    private String degreeOfUseFullness;

    public ArticleDetail(){}

    public ArticleDetail(Article article){
        this.id = article.getId();
        this.time = article.getTime();
        this.name = article.getName();
        this.qty = article.getQty();
        this.price = article.getPrice();
        this.degreeOfUseFullness = article.getDegreeOfUseFullness().toString();
        this.personId = article.getDailyExpense().getPerson().getId();
        this.dailyId = article.getDailyExpense().getId();
    }

}
