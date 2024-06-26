package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.ProductImage;
import edu.icet.pos.entity.ProductImageEntity;

public interface ProductImageBo extends SuperBo {
    boolean save(ProductImage dto);
}
