package org.haulmont.tumandeev.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CREDITS")
@Data
public class Credit extends AbstractModelClass implements Comparable<Credit> {

    @NotNull
    @Column(name = "CREDIT_LIMIT")
    private Long creditLimit;

    @NotNull
    @Column(name = "CREDIT_PERCENT")
    private Double creditPercent;


    public Credit() {
    }

    public Credit(Long creditLimit, Double creditPercent) {
        this.creditLimit = creditLimit;
        this.creditPercent = creditPercent;
    }

    @Override
    public String toString() {
        return this.creditLimit + " руб., " + this.creditPercent + " %";
    }

    @Override
    public int compareTo(Credit o) {
        return o.getCreditLimit().compareTo(this.getCreditLimit());
    }
}