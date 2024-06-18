package edu.icet.pos.dto.tableDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartTable {
    private String productId;
    private String productName;
    private double unitPrice;
    private int productQuantity;
    private double price;
    private double discount;
    private String stockId;
}
