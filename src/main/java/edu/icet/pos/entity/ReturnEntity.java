package edu.icet.pos.entity;

import edu.icet.pos.util.ReturnReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "returns")
public class ReturnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;

    @Column(name = "product_id", nullable = false)
    private String productId;
    @Column(name = "quantity", nullable = false)
    private int returnedQty;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "reason", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReturnReason returnReason;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean isCreditNoteGenerated;
    @Column(name = "return_date", nullable = false)
    private Date returnDate;

    public ReturnEntity(OrderEntity orderEntity, String productId, int returnedQty, double price, ReturnReason returnReason, String description, boolean isCreditNoteGenerated, Date returnDate){
        this.orderEntity = orderEntity;
        this.productId = productId;
        this.returnedQty = returnedQty;
        this.price = price;
        this.returnReason = returnReason;
        this.description = description;
        this.isCreditNoteGenerated = isCreditNoteGenerated;
        this.returnDate = returnDate;
    }
}
