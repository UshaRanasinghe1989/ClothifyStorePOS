package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Employee;
import javafx.collections.ObservableList;

import java.util.List;

public interface EmployeeBo extends SuperBo {
    boolean save(Employee dto);

    List retrieveAll();
    Employee retrieveById(String id);
    List retrieveAllId();
    int update(Employee dto);
    int deleteById(String id);
}
