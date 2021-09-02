package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyExpenseRepository extends JpaRepository<DailyExpense,Long> {
    List<DailyExpense> findAllByPersonIdAndDateBetween(Long id, LocalDate date1,LocalDate date2);
    Optional<DailyExpense> findByPersonIdAndDate(Long id, LocalDate date);
}
