package org.haulmont.tumandeev;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "PAYMENT_SCHEDULES")
@Data
public class PaymentSchedule {
    @Id
    @GeneratedValue
    @Column(name = "PAYMENT_SCHEDULE_ID")
    private Long id;

    @NotNull
    @Column(name = "DAY_OF_PAYMENT")
    private Short dayOfPayment;

    @NotNull
    @Column(name = "PAYMENT_AMOUNT")
    private Long paymentAmount;

    @NotNull
    @Column(name = "REPAYMENT_BODY")
    private Long repaymentBody;

    @NotNull
    @Column(name = "REPAYMENT_PROCENT")
    private Long repaymentProcent;

    public PaymentSchedule(Short dayOfPayment, Long paymentAmount, Long repaymentBody, Long repaymentProcent) {
        this.dayOfPayment = dayOfPayment;
        this.paymentAmount = paymentAmount;
        this.repaymentBody = repaymentBody;
        this.repaymentProcent = repaymentProcent;
    }

    public PaymentSchedule() {}
}