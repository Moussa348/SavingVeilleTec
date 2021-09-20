package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class DailyAnalytic implements Serializable {
    private Map<DegreeOfUseFullness,ArticleDetail> mostExpensiveArticleByUseFullness;
    private Map<DegreeOfUseFullness,ArticleDetail> lessExpensiveArticleByUseFullness;
    private ArticleDetail mostExpensiveArticle, lessExpensiveArticle;
    private Map<DegreeOfUseFullness,Float> totalByUseFullness;
    private Float totalPrice;
}
