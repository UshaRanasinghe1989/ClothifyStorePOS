package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean save(EmployeeEntity entity) {
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
        List list = session.createQuery("FROM EmployeeEntity", EmployeeEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public Employee retrieveById(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List list = session.createQuery("FROM EmployeeEntity E WHERE E.id='"+id+"'").list();
        session.getTransaction().commit();
        session.close();
        return new ModelMapper().map(list.get(0), Employee.class);
    }

    @Override
    public List retrieveAllId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        String query = "SELECT E.id FROM EmployeeEntity E";
        List list = session.createQuery(query).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public int update(EmployeeEntity entity) {
        String sql = "UPDATE EmployeeEntity E SET "+
                        "E.name = :name, "+
                        "E.dob = :dob, "+
                        "E.nic = :nic, "+
                        "E.contactNo = :contactNo, "+
                        "E.email = :email, "+
                        "E.address = :address "+
                        "WHERE E.id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql);
        query.setParameter("name", entity.getName());
        query.setParameter("dob", entity.getDob());
        query.setParameter("nic", entity.getNic());
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
    public int deleteById(String id) {
        String sql = "DELETE FROM EmployeeEntity E WHERE id = :id";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql);
        query.setParameter("id", id);
        int noRowsDeleted = query.executeUpdate();
        transaction.commit();
        session.close();
        return noRowsDeleted;
    }
}
