package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<UserEntity> retrieveAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery("FROM UserEntity", UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<UserEntity> retrieveById(String id) {
        String sql = "FROM UserEntity U WHERE U.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery(sql, UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public UserEntity retrieveByEmpId(String empId) throws IndexOutOfBoundsException {
        String sql = "FROM UserEntity U WHERE U.employeeEntity.id='"+empId+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery(sql, UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list.get(0);
    }

    @Override
    public UserEntity retrieveByUsername(String username) {
        String sql = "FROM UserEntity U WHERE U.email='"+username+"' AND U.isActive=true";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery(sql, UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list.get(0);
    }

    @Override
    public List<String> retrieveAllId() {
        String query = "SELECT U.id FROM UserEntity U";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(query, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(UserEntity entity) {
        String sql = "UPDATE UserEntity U SET "+
                "U.systemName = :systemName, "+
                "U.type = :type, "+
                "U.password = :pw "+
                "WHERE U.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql, UserEntity.class);
        query.setParameter("systemName", entity.getSystemName());
        query.setParameter("type", entity.getType());
        query.setParameter("pw", entity.getPassword());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }

    @Override
    public int deactivateById(UserEntity entity) {
        String sql = "UPDATE UserEntity U SET "+
                "U.isActive = :isActive "+
                "WHERE U.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql, UserEntity.class);
        query.setParameter("isActive", entity.getIsActive());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }

    @Override
    public List<String> retrieveByEmail(String email) {
        String hql = "SELECT U.email FROM UserEntity U WHERE U.email=:email";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(hql, String.class);
        query.setParameter("email", email);
        List<String> emailList = query.getResultList();
        transaction.commit();
        session.close();
        return emailList;
    }

    @Override
    public int resetPassword(String email, String password) {
        String hql = "UPDATE UserEntity U SET "+
                "U.password = :pw "+
                "WHERE U.email = :email";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MutationQuery query = session.createMutationQuery(hql);
        query.setParameter("pw", password);
        query.setParameter("email", email);
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }
}
