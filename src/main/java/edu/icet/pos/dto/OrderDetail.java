package edu.icet.pos.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {
    private int detailId;
    private Orders orders;
    private Product product;
    private String stockId;
    private int quantity;
    private double price;
    private double discount;
    private Boolean isReturned;

    public OrderDetail(Orders orders, Product product, String stockId, int quantity, Double price, double discount, Boolean isReturned) {
        this.orders = orders;
        this.product = product;
        this.stockId = stockId;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.isReturned = isReturned;
    }
}
