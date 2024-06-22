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
public class CreditNote {
    private int id;
    private Returns returns;
    private double price;
    private boolean isRedeemed;
    private Date createDate;

    public CreditNote(Returns returns, double price, boolean isRedeemed, Date createDate){
        this.returns = returns;
        this.price = price;
        this.isRedeemed = isRedeemed;
        this.createDate = createDate;
    }
}
