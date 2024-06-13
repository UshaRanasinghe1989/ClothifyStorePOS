package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public boolean save(CategoryEntity entity) {
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
        List<CategoryEntity> list = session.createQuery("FROM CategoryEntity", CategoryEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List retrieveById(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<CategoryEntity> list = session.createQuery("FROM CategoryEntity C WHERE C.id='"+id+"'", CategoryEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List retrieveAllId() {
        String query = "SELECT C.id FROM CategoryEntity C";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<CategoryEntity> list = session.createQuery(query, CategoryEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(CategoryEntity entity) {
        String sql = "UPDATE CategoryEntity C SET C.name = :name WHERE C.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql, CategoryEntity.class);
        query.setParameter("name", entity.getName());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }

    @Override
    public List retrieveCategoryNames() {
        String sql = "SELECT C.name FROM CategoryEntity C";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List retrieveCatIdByName(String name) {
        String sql = "SELECT C.id FROM CategoryEntity C WHERE C.name='"+name+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
