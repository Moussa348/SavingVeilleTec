package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyExpenseRepository extends JpaRepository<DailyExpense,Long> {
}
