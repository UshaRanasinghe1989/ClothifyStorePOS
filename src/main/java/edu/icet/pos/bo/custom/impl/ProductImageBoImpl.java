package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ProductImageBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ProductImageDao;
import edu.icet.pos.dto.ProductImage;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.ProductImageEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;

public class ProductImageBoImpl implements ProductImageBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private final ProductImageDao productImageDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT_IMAGE);
    @Override
    public boolean save(ProductImage dto) {
        ProductEntity productEntity = mapper.map(dto.getProduct(), ProductEntity.class);
        ProductImageEntity productImageEntity = new ProductImageEntity(
                productEntity,
                dto.getDescription(),
                dto.getImageData()
        );
        return productImageDao.save(productImageEntity);
    }
}
