package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {
}
