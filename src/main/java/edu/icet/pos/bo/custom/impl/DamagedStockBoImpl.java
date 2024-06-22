package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.DamagedStockBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.DamagedStockDao;
import edu.icet.pos.dto.DamagedStock;
import edu.icet.pos.entity.DamagedStockEntity;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;

public class DamagedStockBoImpl implements DamagedStockBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private final DamagedStockDao damagedStockDao = DaoFactory.getInstance().getDao(DaoType.DAMAGED_STOCK);

    @Override
    public boolean save(DamagedStock dto) {
        DamagedStockEntity damagedStockEntity = new DamagedStockEntity(
                mapper.map(dto.getProduct(), ProductEntity.class),
                dto.getQuantity(),
                dto.getDescription(),
                dto.getCreateDate()
        );
        return damagedStockDao.save(damagedStockEntity);
    }
}
