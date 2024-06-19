package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.CustomerDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.CustomerEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<CustomerEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<CustomerEntity> retrieveById(String id) {
        String sql = "FROM CustomerEntity C WHERE C.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<CustomerEntity> list = session.createQuery(sql, CustomerEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(CustomerEntity entity) {
        String sql = "UPDATE CategoryEntity C SET C.name = :name, C.email = :email WHERE C.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql, CategoryEntity.class);
        query.setParameter("name", entity.getName());
        query.setParameter("email", entity.getEmail());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }

    @Override
    public List<CustomerEntity> retrieveByContactNo(String contactNo) {
        String sql = "FROM CustomerEntity C WHERE C.contactNo='"+contactNo+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<CustomerEntity> list = session.createQuery(sql, CustomerEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
