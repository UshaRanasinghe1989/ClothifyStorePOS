package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;

import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetailEntity> {
    List<OrderDetailEntity> retrieveByOrderId(OrderEntity orderEntity);
    int retrieveCountOrderId(OrderEntity orderEntity);
}
