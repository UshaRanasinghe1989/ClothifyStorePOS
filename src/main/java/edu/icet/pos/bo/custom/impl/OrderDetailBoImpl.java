package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Product;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public boolean save(OrderDetail dto) {
        return orderDetailDao.save(new ModelMapper().map(dto, OrderDetailEntity.class));
    }

    @Override
    public List<OrderDetail> retrieveAll() {
        return null;
    }

    @Override
    public List<OrderDetail> retrieveById(String orderId) {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailDao.retrieveById(orderId);
        List<OrderDetail> orderDetailList = new ArrayList<>();

        orderDetailEntityList.forEach(orderDetailEntity ->
                orderDetailList.add(
                        new ModelMapper().map(orderDetailEntity, OrderDetail.class)
                ));
        return orderDetailList;
    }

    @Override
    public List<Integer> retrieveAllId() {
        return null;
    }

    @Override
    public int update(OrderDetail dto) {
        return 0;
    }

    @Override
    public List<OrderDetail> retrieveByOrderId(String orderId) {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailDao.retrieveByOrderId(orderId);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailEntityList.forEach(orderDetailEntity -> {
            orderDetailList.add(
                    new ModelMapper().map(orderDetailEntity, OrderDetail.class)
            );
        });
        return orderDetailList;
    }

    @Override
    public int retrieveCountOrderId(String orderId) {
        return orderDetailDao.retrieveCountOrderId(orderId);
    }
}
