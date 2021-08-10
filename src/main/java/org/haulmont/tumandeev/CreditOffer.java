package org.haulmont.tumandeev;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "CREDIT_OFFERS")
@Data
public class CreditOffer {
    @Id
    @GeneratedValue
    @Column(name = "CREDIT_OFFERS_ID")
    private Long id;

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
}