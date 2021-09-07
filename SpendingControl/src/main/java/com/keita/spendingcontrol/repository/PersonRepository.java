package com.keita.spendingcontrol.repository;

import com.keita.spendingcontrol.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    boolean existsByEmail(String email);
    List<Person> findAllByActiveTrue();
    Optional<Person> findByEmailAndPassword(String email,String password);
}
