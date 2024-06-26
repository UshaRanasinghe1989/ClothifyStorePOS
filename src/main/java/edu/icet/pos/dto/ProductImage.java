package edu.icet.pos.dto;

import edu.icet.pos.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductImage {
    private int id;
    private Product product;
    private String description;
    private byte[] imageData;

    public ProductImage(Product product, String description, byte[] imageData){
        this.product = product;
        this.description = description;
        this.imageData = imageData;
    }
}
