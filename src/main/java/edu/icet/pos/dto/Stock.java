package edu.icet.pos.dto;

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
    private Product product;
    private int initialQty;
    private int availableQty;
    private double unitPrice;
    private Boolean isActive;
    private Date createDateTime;

    public Stock(String id, Product product, int initialQty, int availableQty, double unitPrice) {
        this.id = id;
        this.product = product;
        this.initialQty = initialQty;
        this.availableQty = availableQty;
        this.unitPrice = unitPrice;
    }
}
