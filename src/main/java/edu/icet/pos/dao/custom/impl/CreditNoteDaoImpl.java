package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.CreditNoteDao;
import edu.icet.pos.entity.CreditNoteEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CreditNoteDaoImpl implements CreditNoteDao {
    @Override
    public boolean save(CreditNoteEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<CreditNoteEntity> retrieveAll() {
        return null;
    }

    @Override
    public List<CreditNoteEntity> retrieveById(String id) {
        return null;
    }

    @Override
    public List<String> retrieveAllId() {
        return null;
    }

    @Override
    public int update(CreditNoteEntity entity) {
        return 0;
    }
}
