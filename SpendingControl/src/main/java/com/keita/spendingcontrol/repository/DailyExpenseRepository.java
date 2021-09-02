package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyExpenseRepository extends JpaRepository<DailyExpense,Long> {
    List<DailyExpense> findAllByPersonIdAndDateBetween(Long id, LocalDate date1,LocalDate date2);
}
