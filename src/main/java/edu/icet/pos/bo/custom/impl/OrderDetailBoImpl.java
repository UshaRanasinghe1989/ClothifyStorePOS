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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public boolean save(List<OrderDetail> list) {
        OrderDetail dto=null;
        OrderDetailEntity orderDetailEntity=null;
        boolean isSaved = false;
        for (OrderDetail orderDetail : list) {
            dto = orderDetail;

            orderDetailEntity = new OrderDetailEntity(
                    new ModelMapper().map(dto.getOrders(), OrderEntity.class),
                    new ModelMapper().map(dto.getProduct(), ProductEntity.class),
                    dto.getStockId(),
                    dto.getQuantity(),
                    dto.getPrice(),
                    dto.getDiscount(),
                    dto.isReturned()
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
    public List<OrderDetail> retrieveByOrderId(Orders dto) {
        OrderEntity orderEntity = new ModelMapper().map(dto, OrderEntity.class);
        List<OrderDetailEntity> orderDetailEntityList = orderDetailDao.retrieveByOrderId(orderEntity);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailEntityList.forEach(orderDetailEntity -> {
            OrderDetail orderDetail = new OrderDetail(
                    orderDetailEntity.getDetailId(),
                    new ModelMapper().map(orderDetailEntity.getOrderEntity(), Orders.class),
                    new ModelMapper().map(orderDetailEntity.getProductEntity(), Product.class),
                    orderDetailEntity.getStockId(),
                    orderDetailEntity.getQuantity(),
                    orderDetailEntity.getPrice(),
                    orderDetailEntity.getDiscount(),
                    orderDetailEntity.isReturned()
            );

            orderDetailList.add(orderDetail);
        });

        return orderDetailList;
    }

    @Override
    public List<Product> retrieveProductsByOrderId(Orders dto) {
        List<Product> productList = new ArrayList<>();
        List<ProductEntity> productEntityList =
                orderDetailDao.retrieveProductsByOrderId(
                        new ModelMapper().map(dto, OrderEntity.class));

        productEntityList.forEach(productEntity -> {
            productList.add(new ModelMapper().map(productEntity, Product.class));
        });

        return productList;
    }

    @Override
    public int updateReturnStatus(Orders ordersDto, Product productDto) {
        return 0;
    }
}
