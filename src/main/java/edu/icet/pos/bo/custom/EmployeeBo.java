package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Employee;

import java.util.List;

public interface EmployeeBo extends SuperBo {
    boolean save(Employee dto);

    List<Employee> retrieveAll();
    List<Employee> retrieveById(String id);
    List<Employee> retrieveAllId();
    int update(Employee dto);
    int deleteById(String id);
}
