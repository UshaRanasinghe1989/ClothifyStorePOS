package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.UserEntity;

import java.util.List;

public interface UserDao extends CrudDao<UserEntity> {
    UserEntity retrieveByEmpId(String empId);
    UserEntity retrieveByUsername(String username);
    int deactivateById(UserEntity entity);
    List<String> retrieveByEmail(String email);
    int resetPassword(String email, String password);
}
