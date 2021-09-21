package com.keita.spendingcontrol.model.dto;

import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyAnalytic implements Serializable {
    private Map<DegreeOfUseFullness,ArticleDetail> mostExpensiveArticlesByUseFullness,lessExpensiveArticlesByUseFullness;
    private ArticleDetail mostExpensiveArticle, lessExpensiveArticle;
    private Map<DegreeOfUseFullness,Float> totalByUseFullness;
    private Float totalPrice;
}
