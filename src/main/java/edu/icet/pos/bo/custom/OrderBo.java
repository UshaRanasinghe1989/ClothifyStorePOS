package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.entity.OrderEntity;

import java.util.List;

public interface OrderBo extends SuperBo {
    boolean save(Orders dto);
    List<Orders> retrieveAll();
    List<Orders> retrieveById(String id);
    List<String> retrieveAllId();
    int update(Orders dto);
}
