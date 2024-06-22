package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.DamagedStockDao;
import edu.icet.pos.entity.DamagedStockEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class DamagedStockDaoImpl implements DamagedStockDao {
    @Override
    public boolean save(DamagedStockEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<DamagedStockEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<DamagedStockEntity> retrieveById(String id) {
        return null;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(DamagedStockEntity entity) {
        return 0;
    }
}
