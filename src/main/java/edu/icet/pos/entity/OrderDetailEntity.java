package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_detail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(nullable = false)
    private String stockId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @ColumnDefault("0.0")
    private double discount;

    public OrderDetailEntity(OrderEntity orderEntity, ProductEntity productEntity, String stockId, int quantity, double price, double discount){
        this.orderEntity = orderEntity;
        this.productEntity = productEntity;
        this.stockId = stockId;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }
}
