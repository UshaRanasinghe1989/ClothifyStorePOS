package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

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
    public List retrieveAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List list = session.createQuery("FROM UserEntity", UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<String> retrieveById(String id) {
        String sql = "FROM UserEntity U WHERE U.id='"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public User retrieveByEmpId(EmployeeEntity entity) throws IndexOutOfBoundsException {
        String sql = "FROM UserEntity U WHERE U.employeeEntity.id='"+entity.getId()+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery(sql, UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return new ModelMapper().map(list.get(0), User.class);
    }

    @Override
    public User retrieveByUsername(String username) {
        String sql = "FROM UserEntity U WHERE U.email='"+username+"' AND U.isActive=true";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> list = session.createQuery(sql, UserEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return new ModelMapper().map(list.get(0), User.class);
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
}
