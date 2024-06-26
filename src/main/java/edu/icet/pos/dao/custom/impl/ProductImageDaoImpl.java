package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ProductImageDao;
import edu.icet.pos.entity.ProductImageEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ProductImageDaoImpl implements ProductImageDao {
    @Override
    public boolean save(ProductImageEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<ProductImageEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<ProductImageEntity> retrieveById(String id) {
        return null;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(ProductImageEntity entity) {
        return 0;
    }
}
