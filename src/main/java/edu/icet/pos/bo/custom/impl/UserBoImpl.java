package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean save(User dto) {
        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public List<User> retrieveAll() {
        List<UserEntity> userEntityList = userDao.retrieveAll();
        List<User> userList = new ArrayList<>();

        userEntityList.forEach(userEntity ->
                userList.add(
                        new ModelMapper().map(userEntity, User.class)
                ));

        return userList;
    }

    @Override
    public List<User> retrieveById(String id) {
        List<UserEntity> userEntityList = userDao.retrieveById(id);
        List<User> userList = new ArrayList<>();

        userEntityList.forEach(userEntity ->
                userList.add(
                        new ModelMapper().map(userEntity, User.class)
                ));

        return userList;
    }

    @Override
    public User retrieveByEmpId(String empId) {
        UserEntity userEntity = userDao.retrieveByEmpId(empId);
        return new ModelMapper().map(userEntity, User.class);
    }

    @Override
    public User retrieveByUsername(String username) {
        UserEntity userEntity = userDao.retrieveByUsername(username);
        return new ModelMapper().map(userEntity, User.class);
    }

    @Override
    public List<String> retrieveAllId() {
        return userDao.retrieveAllId();
    }

    @Override
    public int update(User dto) {
        return userDao.update(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public int deactivateById(User dto) {
        return userDao.deactivateById(new ModelMapper().map(dto, UserEntity.class));
    }
}
