package edu.icet.pos.dto;

import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class Payment {
    private int id;
    private Orders orders;
    private PaymentType paymentType;
    private double paidAmount;
    private double balanceAmount;
    private Date paymentDateTime;

    public Payment(Orders orders, PaymentType paymentType, double paidAmount, double balanceAmount, Date dateTime){
        this.orders = orders;
        this.paymentType = paymentType;
        this.paidAmount = paidAmount;
        this.balanceAmount = balanceAmount;
        paymentDateTime = dateTime;
    }
}
