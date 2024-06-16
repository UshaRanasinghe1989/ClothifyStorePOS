package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity> {
    UserEntity retrieveByEmpId(String empId);
    UserEntity retrieveByUsername(String username);
    int deactivateById(UserEntity entity);
}
