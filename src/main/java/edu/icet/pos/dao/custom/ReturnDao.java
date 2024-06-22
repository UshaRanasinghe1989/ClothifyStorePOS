package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ReturnEntity;

import java.util.List;

public interface ReturnDao extends CrudDao<ReturnEntity> {
    List<ReturnEntity> retrieveReturnByOrder(OrderEntity entity);
    int updateCreditNoteStatus(int id);
}
