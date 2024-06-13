package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.dto.Product;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ProductBoImpl implements ProductBo {
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    @Override
    public boolean save(Product dto) {
        return productDao.save(new ModelMapper().map(dto, ProductEntity.class));
    }

    @Override
    public List retrieveAll() {
        return productDao.retrieveAll();
    }

    @Override
    public List retrieveById(String id) {
        return productDao.retrieveById(id);
    }

    @Override
    public List retrieveAllId() {
        return productDao.retrieveAllId();
    }

    @Override
    public int update(Product dto) {
        return productDao.update(new ModelMapper().map(dto, ProductEntity.class));
    }
}
