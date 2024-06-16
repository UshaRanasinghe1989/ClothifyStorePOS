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
public class Orders {
    private String id;
    //private String sellerId;
    //private String cashierId;
    private int noOfProducts;
    private double grossAmount;
    private double discount;
    private double netAmount;
    //private int terminalId;
    private Date orderDate;
}
