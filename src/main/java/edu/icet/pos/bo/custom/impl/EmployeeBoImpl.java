package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean save(Employee dto) {
        return employeeDao.save(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public List<Employee> retrieveAll() {
        return null;
    }

    @Override
    public List retrieveLastId() {
        return employeeDao.retrieveLastId();
    }

    @Override
    public boolean update(Employee dto) {
        return false;
    }

    @Override
    public boolean deactivate_by_id(int id) {
        return false;
    }
}
