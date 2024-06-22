package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private static final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean save(Employee dto) {
        return employeeDao.save(mapper.map(dto, EmployeeEntity.class));
    }

    @Override
    public List<Employee> retrieveAll() throws NullPointerException {
        List<EmployeeEntity> employeeEntityList = employeeDao.retrieveAll();
        return mapper.map(employeeEntityList, new TypeToken<List<Employee>>() {}.getType());
    }

    @Override
    public List<Employee> retrieveById(String id) throws NullPointerException {
        List<EmployeeEntity> employeeEntityList = employeeDao.retrieveAll();
        return mapper.map(employeeEntityList, new TypeToken<List<Employee>>() {}.getType());
    }

    @Override
    public List<String> retrieveAllId(){ return employeeDao.retrieveAllId(); }

    @Override
    public int update(Employee dto) {
        return employeeDao.update(mapper.map(dto, EmployeeEntity.class));
    }

    @Override
    public int deleteById(String id) {
        return employeeDao.deleteById(id);
    }
}
