package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAllByDailyExpenseId(Long id);
}
