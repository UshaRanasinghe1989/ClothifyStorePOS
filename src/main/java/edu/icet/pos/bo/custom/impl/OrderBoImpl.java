package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.entity.*;
import edu.icet.pos.util.DaoType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDERS);

    @Override
    public boolean save(Orders dto) {
        return orderDao.save(new ModelMapper().map(dto, OrderEntity.class));
    }

    @Override
    public List<Orders> retrieveAll() {
        List<OrderEntity> orderEntityList = orderDao.retrieveAll();
        List<Orders> ordersList = new ArrayList<>();

        orderEntityList.forEach(orderEntity ->
                ordersList.add(
                        new ModelMapper().map(orderEntity, Orders.class)));

        return ordersList;
    }

    @Override
    public List<Orders> retrieveById(String id) {
        List<OrderEntity> orderEntityList = orderDao.retrieveById(id);
        List<Orders> ordersList = new ArrayList<>();

        orderEntityList.forEach(orderEntity ->
                ordersList.add(
                        new ModelMapper().map(orderEntity, Orders.class)));

        return ordersList;
    }

    @Override
    public List<String> retrieveAllId() {
        return orderDao.retrieveAllId();
    }

    @Override
    public int update(Orders dto) {
        return 0;
    }

    @Override
    public void addOrderDetail(OrderDetail orderDetailDto) {

        OrderDetailEntity orderDetailEntity = new OrderDetailEntity(
                new ModelMapper().map(orderDetailDto.getOrders(), OrderEntity.class),
                new ModelMapper().map(orderDetailDto.getProduct(), ProductEntity.class),
                orderDetailDto.getStockId(),
                orderDetailDto.getQuantity(),
                orderDetailDto.getPrice(),
                orderDetailDto.getDiscount()
        );

        new OrderEntity().addOrderDetail(orderDetailEntity);
    }

    @Override
    public Set<OrderDetail> retrieveOrderDetailSet() {
        Set<OrderDetailEntity> orderDetailEntitySet = orderDao.retrieveOrderDetailSet();
        Set<OrderDetail> orderDetailSet = new HashSet<>();

        orderDetailEntitySet.forEach(orderDetailEntity -> {
            OrderDetail orderDetail = new ModelMapper().map(orderDetailEntity, OrderDetail.class);
            orderDetailSet.add(orderDetail);
        });
        return orderDetailSet;
    }
}
