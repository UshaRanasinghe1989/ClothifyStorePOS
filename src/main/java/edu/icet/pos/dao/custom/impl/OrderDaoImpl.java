package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.HibernateUtil;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

@Slf4j
@ToString
public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
    @Override
    public List<OrderEntity> retrieveAll() {
        String sql = "FROM OrderEntity";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderEntity> list = session.createQuery(sql, OrderEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<OrderEntity> retrieveById(String id) {
        String sql = "FROM OrderEntity O WHERE O.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderEntity> list = session.createQuery(sql, OrderEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<String> retrieveAllId() {
        String sql = "SELECT O.id FROM OrderEntity O";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(OrderEntity entity) {
        return 0;
    }

    @Override
    public Set<OrderDetailEntity> retrieveOrderDetailSet() {
        return new OrderEntity().getOrderDetailEntitySet();
    }
}
