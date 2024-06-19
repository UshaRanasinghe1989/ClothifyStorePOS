package edu.icet.pos.entity;

import edu.icet.pos.util.PaymentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private OrderEntity orderEntity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private double paidAmount;

    @Column(nullable = false)
    private double balanceAmount;

    @Column(nullable = false)
    private Date paymentDateTime;

    public PaymentEntity(OrderEntity orderEntity, PaymentType paymentType, double paidAmount, double balanceAmount, Date dateTime){
        this.orderEntity = orderEntity;
        this.paymentType = paymentType;
        this.paidAmount = paidAmount;
        this.balanceAmount = balanceAmount;
        paymentDateTime = dateTime;
    }
}
