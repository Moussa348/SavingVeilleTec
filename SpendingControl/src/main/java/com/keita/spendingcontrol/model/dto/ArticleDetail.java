package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleDetail implements Serializable {
    private Long id,personId;
    private LocalDateTime time;
    private String name;
    private Integer qty;
    private Float price;
    private DegreeOfUtility degreeOfUtility;

    public ArticleDetail(){}

    public ArticleDetail(Article article){
        this.id = article.getId();
        this.time = article.getTime();
        this.name = article.getName();
        this.qty = article.getQty();
        this.price = article.getPrice();
        this.personId = article.getDailyExpense().getPerson().getId();
    }

}
