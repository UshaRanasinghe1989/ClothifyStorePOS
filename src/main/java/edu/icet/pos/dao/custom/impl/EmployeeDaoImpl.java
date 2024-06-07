package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    Session session;
    public EmployeeDaoImpl(){
        session = HibernateUtil.getSession();
        session.getTransaction().begin();
    }
    @Override
    public boolean save(EmployeeEntity entity) {
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Employee> retrieveAll() {
        session.getTransaction().begin();
        return session.createQuery("FROM employee", Employee.class).list();
    }

    @Override
    public List retrieveLastId() {
        String query = "SELECT E.id FROM EmployeeEntity E";
        //String query = "SELECT LAST_INSERTED_ID() AS id";
        //session.getTransaction().begin();
        return session.createQuery(query).list();
    }

    @Override
    public boolean update(EmployeeEntity entity) {
        return false;
    }

    @Override
    public boolean deactivate_by_id(int id) {
        return false;
    }
}
