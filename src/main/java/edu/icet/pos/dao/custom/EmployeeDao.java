package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.EmployeeEntity;

public interface EmployeeDao extends CrudDao<EmployeeEntity> {
    int deleteById(String id);
}
