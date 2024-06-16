package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.ProductEntity;

import java.util.List;

public interface ProductDao extends CrudDao<ProductEntity> {
   List<ProductEntity> retrieveByName(String name);
}
