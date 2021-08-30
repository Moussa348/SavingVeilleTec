package com.keita.spendingcontrol.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Goal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason,articlesToAvoid,report;
    private LocalDate startDate,endDate;
    private Float amount;
    private boolean hasBeenRespected;

    @ManyToOne
    private Person person;

    public Goal(){}

    @Builder
    public Goal(Long id, String reason, String articlesToAvoid, String report, LocalDate startDate, LocalDate endDate, Float amount, boolean hasBeenRespected, Person person) {
        this.id = id;
        this.reason = reason;
        this.articlesToAvoid = articlesToAvoid;
        this.report = report;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.hasBeenRespected = hasBeenRespected;
        this.person = person;
    }
}
