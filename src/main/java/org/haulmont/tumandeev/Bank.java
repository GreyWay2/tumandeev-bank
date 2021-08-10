package org.haulmont.tumandeev;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "BANKS")
@Data
public class Bank extends AbstractModelClass implements Serializable {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Credit creditInBank;

    public Bank(Client client, Credit creditInBank) {
        this.client = client;
        this.creditInBank = creditInBank;
    }

    public Bank() {}

    @Override
    public String toString() {
        return this.client +" " + this.creditInBank;
    }

}