package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ReturnDao;
import edu.icet.pos.entity.ReturnEntity;
import edu.icet.pos.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;

@Slf4j
public class ReturnDaoImpl implements ReturnDao {
    @Override
    public boolean save(ReturnEntity entity) {
        log.info(entity.toString()+"*****");
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<ReturnEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<ReturnEntity> retrieveById(String id) {
        return null;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(ReturnEntity entity) {
        return 0;
    }
}
