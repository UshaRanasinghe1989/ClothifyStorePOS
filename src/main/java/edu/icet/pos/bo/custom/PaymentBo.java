package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Payment;

public interface PaymentBo extends SuperBo {
    boolean save(Payment dto);
}
