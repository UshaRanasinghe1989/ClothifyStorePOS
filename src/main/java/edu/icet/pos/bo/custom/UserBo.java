package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.User;

import java.util.List;

public interface UserBo extends SuperBo {
    boolean save(User dto);
    List<User> retrieveAll();
    List<User> retrieveById(String id);
    User retrieveByEmpId(String empId);
    User retrieveByUsername(String username);
    List<String> retrieveAllId();
    int update(User dto);
    int deactivateById(User dto);
    List<String> retrieveByEmail(String email);
    int resetPassword(String email, String password);
}
