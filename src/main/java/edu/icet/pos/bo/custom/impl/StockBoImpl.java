package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.StockBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.StockDao;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Product;
import edu.icet.pos.dto.Stock;
import edu.icet.pos.dto.tableDto.CartTable;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.StockEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class StockBoImpl implements StockBo {
    StockDao stockDao = DaoFactory.getInstance().getDao(DaoType.STOCK);

    @Override
    public boolean save(Stock dto) {
        /*ProductEntity productEntity = new ModelMapper().map(dto.getProduct(), ProductEntity.class);
        StockEntity stockEntity = new StockEntity(
                dto.getId(),
                productEntity,
                dto.getInitialQty(),
                dto.getAvailableQty(),
                dto.getUnitPrice(),
                dto.getIsActive(),
                dto.getCreateDateTime()
        );*/
        //return stockDao.save(stockEntity);
        return stockDao.save(new ModelMapper().map(dto, StockEntity.class));
    }
    @Override
    public List<Stock> retrieveAll() {
        List<StockEntity> stockEntityList = stockDao.retrieveAll();
        List<Stock> stockList = new ArrayList<>();

        stockEntityList.forEach(stockEntity ->
                stockList.add(
                        new ModelMapper().map(stockEntity, Stock.class)
                ));
        return stockList;
    }

    @Override
    public List<Stock> retrieveById(String id) {
        List<StockEntity> stockEntityList = stockDao.retrieveById(id);
        List<Stock> stockList = new ArrayList<>();

        stockEntityList.forEach(stockEntity ->
                stockList.add(
                        new ModelMapper().map(stockEntity, Stock.class)
                ));
        return stockList;
    }

    @Override
    public List<String> retrieveAllId() {
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

    @Override
    public List<Stock> retrieveActiveStockByProduct(Product dto) {
        List<StockEntity> stockEntityList = stockDao.retrieveActiveStockByProduct(new ModelMapper().map(dto, ProductEntity.class));
        List<Stock> stockList = new ArrayList<>();

        stockEntityList.forEach(stockEntity ->
                stockList.add(
                        new ModelMapper().map(stockEntity, Stock.class)
                ));
        return stockList;
    }

    @Override
    public int updateStockQty(List<CartTable> cartTableList) {
        return stockDao.updateStockQty(cartTableList);
    }
}
