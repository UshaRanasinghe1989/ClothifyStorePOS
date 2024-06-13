package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.StockDao;
import edu.icet.pos.entity.StockEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.List;

public class StockDaoImpl implements StockDao {
    @Override
    public boolean save(StockEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<StockEntity> retrieveAll() {
        String sql = "FROM StockEntity";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<StockEntity> list = session.createQuery(sql, StockEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<StockEntity> retrieveById(String id) {
        String sql = "FROM StockEntity S WHERE S.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<StockEntity> list = session.createQuery(sql, StockEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<StockEntity> retrieveAllId() {
        String sql = "SELECT S.id FROM StockEntity S";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<StockEntity> list  = session.createQuery(sql, StockEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(StockEntity entity) {
        String sql = "UPDATE StockEntity S SET "+
                "S.productEntity = :productEntity, "+
                "S.initialQty = :initialQty, "+
                "S.availableQty = :availableQty, "+
                "S.unitPrice = :unitPrice "+
                "WHERE S.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MutationQuery query = session.createMutationQuery(sql);
        query.setParameter("productEntity", entity.getProductEntity());
        query.setParameter("initialQty", entity.getInitialQty());
        query.setParameter("availableQty", entity.getAvailableQty());
        query.setParameter("unitPrice", entity.getUnitPrice());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }

    @Override
    public int deactivateById(String id) {
        String sql = "UPDATE StockEntity S SET S.isActive = false WHERE S.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MutationQuery query = session.createMutationQuery(sql);
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }
}
