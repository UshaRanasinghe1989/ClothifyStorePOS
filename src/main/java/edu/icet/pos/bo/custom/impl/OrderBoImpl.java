package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

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
}
