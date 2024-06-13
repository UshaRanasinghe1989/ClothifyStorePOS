package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(ProductEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<ProductEntity> retrieveAll() {
        String sql = "FROM ProductEntity P";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<ProductEntity> list = session.createQuery(sql, ProductEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<ProductEntity> retrieveById(String id) {
        String sql = "FROM ProductEntity P WHERE P.id = '"+id+"'";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<ProductEntity> list = session.createQuery(sql, ProductEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<String> retrieveAllId() {
        String sql = "SELECT P.id FROM ProductEntity P";
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery(sql, String.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(ProductEntity entity) {
        String sql = "UPDATE ProductEntity P SET "+
                "P.categoryEntity = :categoryEntity, "+
                "P.supplierEntity = :supplierEntity, "+
                "P.name = :name, "+
                "P.description = :description, "+
                "P.size = :size "+
                "WHERE P.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql, ProductEntity.class);
        query.setParameter("categoryEntity", entity.getCategoryEntity());
        query.setParameter("supplierEntity", entity.getSupplierEntity());
        query.setParameter("name", entity.getName());
        query.setParameter("description", entity.getDescription());
        query.setParameter("size", entity.getSize());
        query.setParameter("id", entity.getId());
        int noRowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsUpdated;
    }
}
