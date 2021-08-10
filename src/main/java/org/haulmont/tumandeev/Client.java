package org.haulmont.tumandeev;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENTS")
@Data
public class Client {

    @Id
    @GeneratedValue
    @Column(name= "CLIENT_ID")
    private Long id;

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstName;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "MIDDLENAME")
    private String middleName;

    @NotNull
    @Column(name = "PASSPORT")
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
        return "Client: " + this.lastName + " " + this.firstName + " " + this.middleName + "Passport: " + this.passport;
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
        Client other = (Client) obj;
        if (getId() == null || other.getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }
}