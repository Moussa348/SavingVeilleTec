package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class DailyAnalytic implements Serializable {
    private Map<DegreeOfUseFullness,ArticleDetail> mostExpensiveArticlesByUseFullness;
    private Map<DegreeOfUseFullness,ArticleDetail> lessExpensiveArticlesByUseFullness;
    private ArticleDetail mostExpensiveArticle, lessExpensiveArticle;
    private Map<DegreeOfUseFullness,Float> totalByUseFullness;
    private Float totalPrice;
}
