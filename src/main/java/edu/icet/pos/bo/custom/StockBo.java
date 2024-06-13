package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Stock;

import java.util.List;

public interface StockBo extends SuperBo {
    boolean save(Stock dto);
    List<Stock> retrieveAll();
    List<Stock> retrieveById(String id);
    List<Stock> retrieveAllId();
    int update(Stock dto);
    int deactivateById(String id);
}
