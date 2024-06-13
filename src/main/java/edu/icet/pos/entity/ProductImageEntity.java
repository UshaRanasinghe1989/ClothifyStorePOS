package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product_image")
public class ProductImageEntity {
    @Id
    private int id;
    //ONE IMAGE WOULD BELONG TO ONE PRODUCT
    //FOREIGN KEY REFERS PRODUCT TABLE
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imagePath;
}
