package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.DamagedStock;
import edu.icet.pos.dto.Product;

public interface DamagedStockBo extends SuperBo {
    boolean save(DamagedStock dto);
}
