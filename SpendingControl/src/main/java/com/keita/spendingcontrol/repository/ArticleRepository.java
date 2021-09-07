package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Article;
import com.keita.spendingcontrol.model.enums.DegreeOfUtility;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAllByDailyExpenseId(Long id);
    List<Article> findAllByDailyExpenseIdAndDegreeOfUtility(Long id, DegreeOfUtility degreeOfUtility, Pageable pageable);
}
