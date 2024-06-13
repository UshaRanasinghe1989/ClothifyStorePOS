package edu.icet.pos.dto;

import edu.icet.pos.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class Stock {
    private String id;
    private ProductEntity productEntity;
    private int initialQty;
    private int availableQty;
    private double unitPrice;
    private Boolean isActive;
    private Date createDateTime;

    public Stock(String id, ProductEntity productEntity, int initialQty, int availableQty, double unitPrice) {
        this.id = id;
        this.productEntity = productEntity;
        this.initialQty = initialQty;
        this.availableQty = availableQty;
        this.unitPrice = unitPrice;
    }
}
