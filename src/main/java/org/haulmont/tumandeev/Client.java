package org.haulmont.tumandeev;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CLIENTS")
@Data
public class Client  extends AbstractModelClass  {

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstName;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "MIDDLENAME")
    private String middleName;

    @NotNull
    @Column(name = "PASSPORT", unique = true)
    private String passport;

    public Client(String firstName, String lastName, String middleName, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.passport = passport;
    }

    public Client() {}

    @Override
    public String toString() {
        return  this.lastName + " " + this.firstName + " " + this.middleName + ", Паспорт: " + this.passport;
    }
}