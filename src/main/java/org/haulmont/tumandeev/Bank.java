package org.haulmont.tumandeev;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "BANKS")
@Data
public class Bank {

    @Id
    @GeneratedValue
    @Column(name= "BANK_ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Client creditInBank;

    public Bank(Client client, Client creditInBank) {
        this.client = client;
        this.creditInBank = creditInBank;
    }

    public Bank() {}

    @Override
    public String toString() {
        return "Bank: " + this.client + " " + this.creditInBank;
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
        Bank other = (Bank) obj;
        if (getId() == null || other.getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }
}
