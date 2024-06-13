package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean save(SupplierEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<SupplierEntity> retrieveAll() {
        String query = "FROM SupplierEntity S";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<SupplierEntity> list = session.createQuery(query, SupplierEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<SupplierEntity> retrieveById(String id) {
        String query = "FROM SupplierEntity S WHERE S.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<SupplierEntity> list = session.createQuery(query, SupplierEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<SupplierEntity> retrieveAllId() {
        String query = "SELECT S.id FROM SupplierEntity S";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<SupplierEntity> list = session.createQuery(query, SupplierEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(SupplierEntity entity) {
        String sql = "UPDATE SupplierEntity S SET "+
                "S.name = :name, "+
                "S.contactPerson = :contactPerson, "+
                "S.contactNo = :contactNo, " +
                "S.email = :email, "+
                "S.address = :address "+
                "WHERE S.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MutationQuery query = session.createMutationQuery(sql);
        query.setParameter("name", entity.getName());
        query.setParameter("contactPerson", entity.getContactPerson());
        query.setParameter("contactNo", entity.getContactNo());
        query.setParameter("email", entity.getEmail());
        query.setParameter("address", entity.getAddress());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }

    @Override
    public List<String> retrieveSupplierNames() {
        String sql = "SELECT S.name FROM SupplierEntity S";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<String> retrieveSupplierIdByName(String name) {
        String sql = "SELECT S.id FROM SupplierEntity S WHERE S.name='"+name+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
