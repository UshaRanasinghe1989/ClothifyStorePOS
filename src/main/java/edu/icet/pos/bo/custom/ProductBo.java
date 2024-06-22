package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.DamagedStock;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Product;
import edu.icet.pos.entity.ProductEntity;

import java.util.List;

public interface ProductBo extends SuperBo {
    boolean save(Product dto);
    List<Product> retrieveAll();
    List<Product> retrieveById(String id);
    List<String> retrieveAllId();
    int update(Product dto);
    List<Product> retrieveByName(String name);
    void addOrderDetail(OrderDetail orderDetailDto);
    void addDamagedStock(DamagedStock damagedStockDto);
}
