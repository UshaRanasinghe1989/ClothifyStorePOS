package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Category;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;

import java.util.List;

public interface OrderDetailBo extends SuperBo {
    boolean save(OrderDetail dto);
    List<OrderDetail> retrieveAll();
    List<OrderDetail> retrieveById(String orderId);
    List<Integer> retrieveAllId();
    int update(OrderDetail dto);
    List<OrderDetail> retrieveByOrderId(String orderId);
    int retrieveCountOrderId(String orderId);
}
