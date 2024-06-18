package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Product;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ProductEntity;

import java.util.List;
import java.util.Set;

public interface OrderDao extends CrudDao<OrderEntity> {
    Set<OrderDetailEntity> retrieveOrderDetailSet();
}
