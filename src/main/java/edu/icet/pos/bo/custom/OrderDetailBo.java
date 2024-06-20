package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Product;

import java.util.List;

public interface OrderDetailBo extends SuperBo {
    boolean save(List<OrderDetail> list);
    List<OrderDetail> retrieveAll();
    List<OrderDetail> retrieveById(String orderId);
    List<Integer> retrieveAllId();
    int update(OrderDetail dto);
    List<OrderDetail> retrieveByOrderId(Orders dto);
    List<Product> retrieveProductsByOrderId(Orders dto);
    int updateReturnStatus(Orders ordersDto, Product productDto);
}
