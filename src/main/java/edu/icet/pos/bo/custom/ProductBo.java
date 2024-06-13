package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Product;

import java.util.List;

public interface ProductBo extends SuperBo {
    boolean save(Product dto);
    List<Product> retrieveAll();
    List<Product> retrieveById(String id);
    List<Product> retrieveAllId();
    int update(Product dto);
}
