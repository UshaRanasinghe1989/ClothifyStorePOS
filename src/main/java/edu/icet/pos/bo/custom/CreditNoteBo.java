package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.CreditNote;

public interface CreditNoteBo extends SuperBo {
    boolean save(CreditNote dto);
}
