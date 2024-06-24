package edu.icet.pos.dto.holder_dto;

import edu.icet.pos.dto.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailsHolder {
    private Orders orders;
    private static OrderDetailsHolder instance;
    private OrderDetailsHolder(){}
    public static OrderDetailsHolder getInstance(){
        if (instance==null){
            instance = new OrderDetailsHolder();
        }
        return instance;
    }
}
