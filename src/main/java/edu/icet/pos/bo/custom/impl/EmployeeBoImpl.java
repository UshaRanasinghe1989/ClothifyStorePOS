package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private final EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean save(Employee dto) {
        return employeeDao.save(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public List<Employee> retrieveAll() throws NullPointerException {
        List<EmployeeEntity> employeeEntityList = employeeDao.retrieveAll();
        List<Employee> employeeList = new ArrayList<>();

        employeeEntityList.forEach(employeeEntity ->
                employeeList.add(
                        new ModelMapper().map(employeeEntity, Employee.class)));

        return employeeList;
    }

    @Override
    public List<Employee> retrieveById(String id) throws NullPointerException {
        List<EmployeeEntity> employeeEntityList = employeeDao.retrieveAll();
        List<Employee> employeeList = new ArrayList<>();

        employeeEntityList.forEach(employeeEntity ->
                employeeList.add(
                    new ModelMapper().map(employeeEntity, Employee.class)));

        return employeeList;
    }

    @Override
    public List<String> retrieveAllId(){ return employeeDao.retrieveAllId(); }

    @Override
    public int update(Employee dto) {
        return employeeDao.update(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public int deleteById(String id) {
        return employeeDao.deleteById(id);
    }

}
