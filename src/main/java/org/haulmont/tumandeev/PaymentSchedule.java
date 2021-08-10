package org.haulmont.tumandeev;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table (name = "PAYMENT_SCHEDULES")
@Data
public class PaymentSchedule extends AbstractModelClass {

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