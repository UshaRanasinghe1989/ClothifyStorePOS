package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
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
    public List<OrderDetailEntity> retrieveByOrderId(String orderId) {
        String sql = "SELECT sum(O.price) price FROM OrderDetailEntity O WHERE O.orderId=:orderId";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query<OrderDetailEntity> query = session.createQuery(sql, OrderDetailEntity.class);
        query.setParameter("orderId", orderId);
        transaction.commit();
        List<OrderDetailEntity> list = query.list();
        session.close();
        return list;
    }

    @Override
    public int retrieveCountOrderId(String orderId) {
        String hql = "SELECT COUNT(distinct O.orderId) FROM OrderDetailEntity.O WHERE O.orderId='"+orderId+"'";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query<Integer> query = session.createQuery(hql, Integer.class);
        transaction.commit();
        return query.uniqueResult();
    }
}
