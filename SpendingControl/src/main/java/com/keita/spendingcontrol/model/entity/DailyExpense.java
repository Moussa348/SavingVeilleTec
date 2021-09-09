package com.keita.spendingcontrol.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DailyExpense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Float total;

    @ManyToOne
    private Person person;


    @OneToMany(mappedBy = "dailyExpense",cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public DailyExpense(){ }

    public DailyExpense(Person person){
        this.date = LocalDate.now();
        this.total = 0.0f;
        this.person = person;
    }

    @Builder
    public DailyExpense(Long id, LocalDate date, Float total,Person person) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.person = person;
    }
}
