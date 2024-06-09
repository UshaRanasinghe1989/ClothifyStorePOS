package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean save(Employee dto) {
        return employeeDao.save(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public List retrieveAll() {
        return employeeDao.retrieveAll();
    }

    @Override
    public Employee retrieveById(String id) {
        return employeeDao.retrieveById(id);
    }

    @Override
    public List retrieveAllId(){ return employeeDao.retrieveAllId(); }

    @Override
    public int update(Employee dto) {
        return employeeDao.update(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public int deleteById(String id) {
        return employeeDao.deleteById(id);
    }

}
