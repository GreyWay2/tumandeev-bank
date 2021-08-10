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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREDIT_ID")
    private Credit credit;

    @NotNull
    @Column(name = "CREDIT_AMOUNT")
    private Long creditAmount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAYMENT_SCHEDULE_ID")
    private PaymentSchedule paymentSchedule;

    @NotNull
    @Column(name = "BANK_ID")
    private long bank_id;

    public CreditOffer(Client client, Credit credit, long creditAmount, PaymentSchedule paymentSchedule, long bank_id) {
        this.client = client;
        this.credit = credit;
        this.creditAmount = creditAmount;
        this.paymentSchedule = paymentSchedule;
        this.bank_id = bank_id;
    }
}