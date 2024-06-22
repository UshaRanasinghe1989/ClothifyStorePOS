package edu.icet.pos.dto;

import edu.icet.pos.util.ReturnReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Returns {
    private int id;
    private Orders orders;
    private String productId;
    private int returnedQty;
    private double price;
    private ReturnReason returnReason;
    private String description;
    private boolean isCreditNoteGenerated;
    private Date returnDate;

    public Returns(Orders orders, String productId, int returnedQty, double price, ReturnReason returnReason, String description, boolean isCreditNoteGenerated, Date returnDate){
        this.orders = orders;
        this.productId = productId;
        this.returnedQty = returnedQty;
        this.price = price;
        this.returnReason = returnReason;
        this.description = description;
        this.isCreditNoteGenerated = isCreditNoteGenerated;
        this.returnDate = returnDate;
    }
}
