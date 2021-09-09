package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.enums.DegreeOfUseFullness;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAllByDailyExpenseId(Long id);
    //List<Article> findAllByDailyExpenseIdAndDegreeOfUseFullness(Long id, DegreeOfUseFullness degreeOfUseFullness, Pageable pageable);
    List<Article>  findAllByDailyExpenseIdAndDegreeOfUseFullness(Long id,DegreeOfUseFullness degreeOfUseFullness,Pageable pageable);
}
