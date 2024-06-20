package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ReturnBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ReturnDao;
import edu.icet.pos.dto.Returns;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ReturnEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class ReturnBoImpl implements ReturnBo {
    private static final ReturnDao returnsDao = DaoFactory.getInstance().getDao(DaoType.RETURN);
    @Override
    public boolean save(Returns dto) {
        ReturnEntity returnEntity = new ReturnEntity(
                new ModelMapper().map(dto.getOrders(), OrderEntity.class),
                dto.getProductId(),
                dto.getReturnedQty(),
                dto.getPrice(),
                dto.getReturnReason(),
                dto.getDescription(),
                dto.getReturnDate()
        );
        return returnsDao.save(returnEntity);
    }
}
