package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "damaged_stock")
public class DamagedStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date createDate;

    public DamagedStockEntity(ProductEntity productEntity, int quantity, String description, Date createDate){
        this.productEntity = productEntity;
        this.quantity = quantity;
        this.description = description;
        this.createDate = createDate;
    }

}
