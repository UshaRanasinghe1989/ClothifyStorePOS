package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "stock")
public class StockEntity {
    @Id
    private String id;

    //ONE STOCK BELONGS TO ONE PRODUCT
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(nullable = false)
    private int initialQty;

    @Column(nullable = false)
    private int availableQty;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private float discount;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDateTime;
}
