package edu.icet.pos.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orders {
    private String orderId;
    private Customer customer;
    //private String sellerId;
    //private String cashierId;
    private int noOfProducts;
    private double grossAmount;
    private double discount;
    private double netAmount;
    //private int terminalId;
    private Date orderDate;
}
