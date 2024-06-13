package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.StockBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.StockDao;
import edu.icet.pos.dto.Stock;
import edu.icet.pos.entity.StockEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class StockBoImpl implements StockBo {
    StockDao stockDao = DaoFactory.getInstance().getDao(DaoType.STOCK);

    @Override
    public boolean save(Stock dto) {
        return stockDao.save(new ModelMapper().map(dto, StockEntity.class));
    }

    @Override
    public List retrieveAll() {
        return stockDao.retrieveAll();
    }

    @Override
    public List retrieveById(String id) {
        return stockDao.retrieveById(id);
    }

    @Override
    public List retrieveAllId() {
        return stockDao.retrieveAllId();
    }

    @Override
    public int update(Stock dto) {
        return stockDao.update(new ModelMapper().map(dto, StockEntity.class));
    }

    @Override
    public int deactivateById(String id) {
        return stockDao.deactivateById(id);
    }
}
