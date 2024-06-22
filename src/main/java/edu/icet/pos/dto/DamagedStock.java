package edu.icet.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DamagedStock {
    private int id;
    private Product product;
    private int quantity;
    private String description;
    private Date createDate;

    public DamagedStock(Product product, int quantity, String description, Date createDate){
        this.product = product;
        this.quantity = quantity;
        this.description = description;
        this.createDate = createDate;
    }
}
