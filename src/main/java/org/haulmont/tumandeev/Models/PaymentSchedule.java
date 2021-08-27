package org.haulmont.tumandeev.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table (name = "PAYMENT_SCHEDULES")
@Data
public class PaymentSchedule extends AbstractModelClass {

    @NotNull
    @Column(name = "DAY_OF_PAYMENT")
    private Date dayOfPayment;

    @NotNull
    @Column(name = "PAYMENT_AMOUNT")
    private double paymentAmount;

    @NotNull
    @Column(name = "REPAYMENT_BODY")
    private double paymentBody;

    @NotNull
    @Column(name = "REPAYMENT_PERCENT")
    private double paymentPercent;


    public PaymentSchedule(Date dayOfPayment, double paymentAmount, double paymentBody,
                           double paymentPercent) {
        this.dayOfPayment = dayOfPayment;
        this.paymentAmount = paymentAmount;
        this.paymentBody = paymentBody;
        this.paymentPercent = paymentPercent;
    }
    public PaymentSchedule() {

    }
    @Override
    public String toString() {
        return "Дата платежа: " + this.dayOfPayment + " Сумма платежа: " + paymentAmount
                + " Тело: " + paymentBody + " Проценты: " + paymentPercent;
    }
}