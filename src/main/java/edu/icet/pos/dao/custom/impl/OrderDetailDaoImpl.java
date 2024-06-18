package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean save(OrderDetailEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List retrieveAll() {
        return null;
    }

    @Override
    public List<OrderDetailEntity> retrieveById(String id) {
        String sql = "FROM OrderDetailEntity O WHERE O.id='"+Integer.parseInt(id)+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderDetailEntity> list = session.createQuery(sql, OrderDetailEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List retrieveAllId() {
        return null;
    }

    @Override
    public int update(OrderDetailEntity entity) {
        return 0;
    }

    @Override
    public List<OrderDetailEntity> retrieveByOrderId(OrderEntity orderEntity) {
        String sql = "FROM OrderDetailEntity O WHERE O.orderEntity=:orderEntity";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query<OrderDetailEntity> query = session.createQuery(sql, OrderDetailEntity.class);
        query.setParameter("orderEntity", orderEntity);
        transaction.commit();
        List<OrderDetailEntity> list = query.list();
        session.close();
        return list;
    }

    @Override
    public int retrieveCountOrderId(OrderEntity orderEntity) {
        String hql = "SELECT COUNT(distinct O.orderEntity) FROM OrderDetailEntity O WHERE O.orderEntity='"+orderEntity+"'";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query<Integer> query = session.createQuery(hql, Integer.class);
        transaction.commit();
        return query.uniqueResult();
    }
}
