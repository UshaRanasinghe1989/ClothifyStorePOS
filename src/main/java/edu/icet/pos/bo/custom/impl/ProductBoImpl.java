package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.dto.DamagedStock;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Product;
import edu.icet.pos.entity.*;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    @Override
    public boolean save(Product dto) {
        CategoryEntity categoryEntity = mapper.map(dto.getCategory(), CategoryEntity.class);
        SupplierEntity supplierEntity = mapper.map(dto.getSupplier(), SupplierEntity.class);
        ProductEntity productEntity = new ProductEntity(
                dto.getId(),
                categoryEntity,
                supplierEntity,
                dto.getName(),
                dto.getDescription(),
                dto.getSize(),
                dto.getCreateDateTime()
        );
        return productDao.save(productEntity);
    }

    @Override
    public List<Product> retrieveAll() {
        List<ProductEntity> productEntityList = productDao.retrieveAll();
        return mapper.map(productEntityList, new TypeToken<List<Product>>() {}.getType());
    }

    @Override
    public List<Product> retrieveById(String id) {
        List<ProductEntity> productEntityList = productDao.retrieveById(id);
        return mapper.map(productEntityList, new TypeToken<List<Product>>() {}.getType());
    }

    @Override
    public List<String> retrieveAllId() {
        return productDao.retrieveAllId();
    }

    @Override
    public int update(Product dto) {
        return productDao.update(mapper.map(dto, ProductEntity.class));
    }

    @Override
    public List<Product> retrieveByName(String name) {
        List<ProductEntity> productEntityList = productDao.retrieveByName(name);
        return mapper.map(productEntityList, new TypeToken<List<Product>>() {}.getType());
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

        new ProductEntity().addOrderDetail(orderDetailEntity);
    }

    @Override
    public void addDamagedStock(DamagedStock damagedStockDto) {
        new ProductEntity().addDamagedStock(mapper.map(damagedStockDto, DamagedStockEntity.class));
    }
}
