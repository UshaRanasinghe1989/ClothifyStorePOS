package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ReturnDao;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ReturnEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.List;

@Slf4j
public class ReturnDaoImpl implements ReturnDao {
    @Override
    public boolean save(ReturnEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<ReturnEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<ReturnEntity> retrieveById(String id) {
        return null;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(ReturnEntity entity) {
        return 0;
    }

    @Override
    public List<ReturnEntity> retrieveReturnByOrder(OrderEntity entity) {
        String hql = "FROM ReturnEntity R WHERE R.orderEntity=:entity AND R.isCreditNoteGenerated=:status";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(hql, ReturnEntity.class);
        query.setParameter("entity", entity);
        query.setParameter("status", false);
        List<ReturnEntity> list = query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public int updateCreditNoteStatus(int id) {
        String hql = "UPDATE ReturnEntity R SET R.isCreditNoteGenerated = :status WHERE R.id=:id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MutationQuery query = session.createMutationQuery(hql);
        query.setParameter("status", true);
        query.setParameter("id", id);
        int updatedRowCount = query.executeUpdate();
        transaction.commit();
        session.close();
        return updatedRowCount;
    }
}
