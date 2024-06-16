package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.dto.Product;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    @Override
    public boolean save(Product dto) {
        CategoryEntity categoryEntity = new ModelMapper().map(dto.getCategory(), CategoryEntity.class);
        SupplierEntity supplierEntity = new ModelMapper().map(dto.getSupplier(), SupplierEntity.class);
        ProductEntity productEntity = new ProductEntity(
                dto.getId(),
                categoryEntity,
                supplierEntity,
                dto.getName(),
                dto.getDescription(),
                dto.getSize(),
                dto.getCreateDateTime(),
                null,
                null
        );
        return productDao.save(productEntity);
    }

    @Override
    public List<Product> retrieveAll() {
        List<ProductEntity> productEntityList = productDao.retrieveAll();
        List<Product> productList = new ArrayList<>();

        productEntityList.forEach(productEntity ->
                productList.add(
                        new ModelMapper().map(productEntity, Product.class)
                ));
        return productList;
    }

    @Override
    public List<Product> retrieveById(String id) {
        List<ProductEntity> productEntityList = productDao.retrieveById(id);
        List<Product> productList = new ArrayList<>();

        productEntityList.forEach(productEntity ->
                productList.add(
                        new ModelMapper().map(productEntity, Product.class)
                ));
        return productList;
    }

    @Override
    public List<String> retrieveAllId() {
        return productDao.retrieveAllId();
    }

    @Override
    public int update(Product dto) {
        return productDao.update(new ModelMapper().map(dto, ProductEntity.class));
    }

    @Override
    public List<Product> retrieveByName(String name) {
        List<ProductEntity> productEntityList = productDao.retrieveByName(name);
        List<Product> productList = new ArrayList<>();

        productEntityList.forEach(productEntity ->
                productList.add(
                        new ModelMapper().map(productEntity, Product.class)
                ));
        return productList;
    }
}
