package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;

import java.util.List;
import java.util.Set;

public interface OrderBo extends SuperBo {
    boolean save(Orders dto);
    List<Orders> retrieveAll();
    List<Orders> retrieveById(String id);
    List<String> retrieveAllId();
    int update(Orders dto);
    void addOrderDetail(OrderDetail orderDetailDto);
    Set<OrderDetail> retrieveOrderDetailSet();
}
