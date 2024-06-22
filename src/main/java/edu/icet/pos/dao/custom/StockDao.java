package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.dto.tableDto.CartTable;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.StockEntity;

import java.util.List;

public interface StockDao extends CrudDao<StockEntity> {
    int deactivateById(String id);
    List<StockEntity> retrieveActiveStockByProduct(ProductEntity entity);
    int updateStockQty(List<CartTable> cartTableList);
    int updateStockQtyReturned(String id, int qty);
}
