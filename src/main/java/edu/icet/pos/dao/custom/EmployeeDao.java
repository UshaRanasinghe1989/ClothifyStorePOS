package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends CrudDao<EmployeeEntity> {
    boolean save(EmployeeEntity entity);

    List<Employee> retrieveAll();

    List retrieveLastId();
    boolean update(EmployeeEntity entity);
    boolean deactivate_by_id(int id);
}
