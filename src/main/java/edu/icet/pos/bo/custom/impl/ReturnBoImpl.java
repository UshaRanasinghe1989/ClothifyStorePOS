package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ReturnBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ReturnDao;
import edu.icet.pos.dto.Category;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Returns;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ReturnEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class ReturnBoImpl implements ReturnBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final ReturnDao returnsDao = DaoFactory.getInstance().getDao(DaoType.RETURN);
    @Override
    public boolean save(Returns dto) {
        ReturnEntity returnEntity = new ReturnEntity(
                mapper.map(dto.getOrders(), OrderEntity.class),
                dto.getProductId(),
                dto.getReturnedQty(),
                dto.getPrice(),
                dto.getReturnReason(),
                dto.getDescription(),
                false,
                dto.getReturnDate()
        );
        return returnsDao.save(returnEntity);
    }

    @Override
    public List<Returns> retrieveReturnByOrder(Orders dto) {
        List<ReturnEntity> returnEntityList = returnsDao.retrieveReturnByOrder(
                mapper.map(dto, OrderEntity.class)
        );
        return mapper.map(returnEntityList, new TypeToken<List<Returns>>() {}.getType());
    }

    @Override
    public int updateCreditNoteStatus(int id) {
        return returnsDao.updateCreditNoteStatus(id);
    }
}
