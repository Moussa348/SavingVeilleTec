package com.keita.spendingcontrol.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class DailyExpense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Float total;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "dailyExpense")
    private List<Article> articles = new ArrayList<>();

    public DailyExpense(){ }

    public DailyExpense(Person person){
        this.date = LocalDate.now();
        this.total = 0.0f;
        this.person = person;
    }

    @Builder
    public DailyExpense(Long id, LocalDate date, Float total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }
}
