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
import edu.icet.pos.util.GetModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDetailBoImpl implements OrderDetailBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public boolean save(List<OrderDetail> list) {
        OrderDetail dto;
        OrderDetailEntity orderDetailEntity;
        boolean isSaved = false;
        for (OrderDetail orderDetail : list) {
            dto = orderDetail;

            orderDetailEntity = new OrderDetailEntity(
                    mapper.map(dto.getOrders(), OrderEntity.class),
                    mapper.map(dto.getProduct(), ProductEntity.class),
                    dto.getStockId(),
                    dto.getQuantity(),
                    dto.getPrice(),
                    dto.getDiscount(),
                    dto.getIsReturned()
            );

            isSaved=orderDetailDao.save(orderDetailEntity);
        }
        if (isSaved){
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDetail> retrieveAll() {
        return null;
    }

    @Override
    public List<OrderDetail> retrieveById(String orderId) {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailDao.retrieveById(orderId);
        return mapper.map(orderDetailEntityList, new TypeToken<List<OrderDetail>>() {}.getType());
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
    public List<OrderDetail> retrieveByOrderId(Orders dto) {
        OrderEntity orderEntity = new ModelMapper().map(dto, OrderEntity.class);
        List<OrderDetailEntity> orderDetailEntityList = orderDetailDao.retrieveByOrderId(orderEntity);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailEntityList.forEach(orderDetailEntity -> {
            OrderDetail orderDetail = new OrderDetail(
                    orderDetailEntity.getDetailId(),
                    mapper.map(orderDetailEntity.getOrderEntity(), Orders.class),
                    mapper.map(orderDetailEntity.getProductEntity(), Product.class),
                    orderDetailEntity.getStockId(),
                    orderDetailEntity.getQuantity(),
                    orderDetailEntity.getPrice(),
                    orderDetailEntity.getDiscount(),
                    orderDetailEntity.getIsReturned()
            );

            orderDetailList.add(orderDetail);
        });

        return orderDetailList;
    }

    @Override
    public List<Product> retrieveProductsByOrderId(Orders dto) {
        List<ProductEntity> productEntityList =
                orderDetailDao.retrieveProductsByOrderId(
                        mapper.map(dto, OrderEntity.class));

        return mapper.map(productEntityList, new TypeToken<List<Product>>() {}.getType());
    }

    @Override
    public int updateReturnStatus(int orderDetailId) {
        return orderDetailDao.updateReturnStatus(orderDetailId);
    }
}
