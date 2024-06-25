package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.entity.*;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class OrderBoImpl implements OrderBo {
    private static final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private final OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDERS);

    @Override
    public boolean save(Orders dto) {
        CustomerEntity customerEntity;
        if (dto.getCustomer()!=null) {
            customerEntity = mapper.map(dto.getCustomer(), CustomerEntity.class);
        }else {
            customerEntity = null;
        }

        OrderEntity orderEntity = new OrderEntity(
                dto.getOrderId(),
                customerEntity,
                dto.getNoOfProducts(),
                dto.getGrossAmount(),
                dto.getDiscount(),
                dto.getNetAmount(),
                dto.getSeller(),
                dto.getCashier(),
                dto.getOrderDate()
        );
        return orderDao.save(orderEntity);
    }

    @Override
    public List<Orders> retrieveAll() {
        List<OrderEntity> orderEntityList = orderDao.retrieveAll();
        return mapper.map(orderEntityList, new TypeToken<List<Orders>>() {}.getType());
    }

    @Override
    public List<Orders> retrieveById(String id) {
        List<OrderEntity> orderEntityList = orderDao.retrieveById(id);

        return mapper.map(orderEntityList, new TypeToken<List<Orders>>() {}.getType());
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
                mapper.map(orderDetailDto.getOrders(), OrderEntity.class),
                mapper.map(orderDetailDto.getProduct(), ProductEntity.class),
                orderDetailDto.getStockId(),
                orderDetailDto.getQuantity(),
                orderDetailDto.getPrice(),
                orderDetailDto.getDiscount(),
                orderDetailDto.getIsReturned()
        );

        new OrderEntity().addOrderDetail(orderDetailEntity);
    }

    @Override
    public Set<OrderDetail> retrieveOrderDetailSet() {
        Set<OrderDetailEntity> orderDetailEntitySet = orderDao.retrieveOrderDetailSet();

        return mapper.map(orderDetailEntitySet, new TypeToken<List<Orders>>() {}.getType());
    }
}
