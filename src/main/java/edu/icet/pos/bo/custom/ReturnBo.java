package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Returns;

public interface ReturnBo extends SuperBo {
    boolean save(Returns dto);
}
