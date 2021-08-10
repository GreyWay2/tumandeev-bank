package org.haulmont.tumandeev;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CREDIT_OFFERS")
@Data
public class CreditOffer extends AbstractModelClass {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @NotNull
    @Column(name = "CREDIT_AMOUNT")
    private Long creditAmount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAYMENT_SCHEDULE_ID")
    private PaymentSchedule paymentSchedule;

    public CreditOffer() {}
}