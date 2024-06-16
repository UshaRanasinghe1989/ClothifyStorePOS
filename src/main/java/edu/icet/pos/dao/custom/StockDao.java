package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.StockEntity;

import java.util.List;

public interface StockDao extends CrudDao<StockEntity> {
    int deactivateById(String id);
    List<StockEntity> retrieveActiveStockByProduct(ProductEntity entity);
}
