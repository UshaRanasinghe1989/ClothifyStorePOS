package edu.icet.pos.entity;

import edu.icet.pos.dto.Product;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //ONE IMAGE WOULD BELONG TO ONE PRODUCT
    //FOREIGN KEY REFERS PRODUCT TABLE
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(nullable = false)
    private String description;

    @Lob
    @Column(name = "image_data", nullable = false, columnDefinition="BLOB")
    private byte[] imageData;

    public ProductImageEntity(ProductEntity productEntity, String description, byte[] imageData){
        this.productEntity = productEntity;
        this.description = description;
        this.imageData = imageData;
    }
}
