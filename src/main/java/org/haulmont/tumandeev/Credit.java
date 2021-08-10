package org.haulmont.tumandeev;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CREDITS")
@Data
public class Credit {

    @Id
    @GeneratedValue
    @Column(name = "CREDIT_ID")
    private Long id;

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

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Credit other = (Credit) obj;
        if (getId() == null || other.getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }
}