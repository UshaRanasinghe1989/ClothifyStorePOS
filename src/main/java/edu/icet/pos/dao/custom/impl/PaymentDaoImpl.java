package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.PaymentDao;
import edu.icet.pos.entity.PaymentEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public boolean save(PaymentEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<PaymentEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<PaymentEntity> retrieveById(String id) {
        return null;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(PaymentEntity entity) {
        return 0;
    }
}
