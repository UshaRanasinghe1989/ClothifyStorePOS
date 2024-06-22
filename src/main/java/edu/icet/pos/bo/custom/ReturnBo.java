package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Returns;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ReturnEntity;

import java.util.List;

public interface ReturnBo extends SuperBo {
    boolean save(Returns dto);
    List<Returns> retrieveReturnByOrder(Orders dto);
    int updateCreditNoteStatus(int id);
}
