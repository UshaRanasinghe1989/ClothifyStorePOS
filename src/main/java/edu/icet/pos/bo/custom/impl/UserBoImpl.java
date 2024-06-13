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

import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean save(User dto) {
        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public List retrieveAll() {
        return userDao.retrieveAll();
    }

    @Override
    public List retrieveById(String id) {
        return userDao.retrieveById(id);
    }

    @Override
    public User retrieveByEmpId(Employee dto) {
        return userDao.retrieveByEmpId(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public User retrieveByUsername(String username) {
        return userDao.retrieveByUsername(username);
    }

    @Override
    public List retrieveAllId() {
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
