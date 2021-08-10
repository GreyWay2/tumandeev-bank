package org.haulmont.tumandeev;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CREDITS")
@Data
public class Credit extends AbstractModelClass  {

    @NotNull
    private Long creditLimit;

    @NotNull
    private Double creditProcent;


    public Credit() {}

    public Credit(Long creditLimit, Double creditProcent) {
        this.creditLimit = creditLimit;
        this.creditProcent = creditProcent;
    }

    @Override
    public String toString() {
        return "Credit: " + this.creditLimit + " " + this.creditProcent;
    }
}