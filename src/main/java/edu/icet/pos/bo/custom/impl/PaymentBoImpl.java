package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.PaymentBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.PaymentDao;
import edu.icet.pos.dto.Payment;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.PaymentEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class PaymentBoImpl implements PaymentBo {
    private static final PaymentDao paymentDao = DaoFactory.getInstance().getDao(DaoType.PAYMENT);
    @Override
    public boolean save(Payment dto) {
        OrderEntity orderEntity = new ModelMapper().map(dto.getOrders(), OrderEntity.class);
        PaymentEntity paymentEntity = new PaymentEntity(
                orderEntity,
                dto.getPaymentType(),
                dto.getPaidAmount(),
                dto.getBalanceAmount(),
                dto.getPaymentDateTime()
        );
        return paymentDao.save(paymentEntity);
    }
}