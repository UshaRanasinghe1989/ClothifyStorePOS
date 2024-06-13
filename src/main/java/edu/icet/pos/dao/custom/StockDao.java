package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.StockEntity;

public interface StockDao extends CrudDao<StockEntity> {
    int deactivateById(String id);
}
