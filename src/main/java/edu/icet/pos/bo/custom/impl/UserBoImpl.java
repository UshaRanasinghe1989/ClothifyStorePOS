package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean save(User dto) {
        return userDao.save(mapper.map(dto, UserEntity.class));
    }

    @Override
    public List<User> retrieveAll() {
        List<UserEntity> userEntityList = userDao.retrieveAll();
        return mapper.map(userEntityList, new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public List<User> retrieveById(String id) {
        List<User> userList = new ArrayList<>();

        List<UserEntity> userEntityList = userDao.retrieveById(id);
        UserEntity userEntity = userEntityList.get(0);

        userList.add(mapper.map(userEntity, User.class));
        return userList;
    }

    @Override
    public User retrieveByEmpId(String empId) {
        UserEntity userEntity = userDao.retrieveByEmpId(empId);
        return mapper.map(userEntity, User.class);
    }

    @Override
    public User retrieveByUsername(String username) {
        UserEntity userEntity = userDao.retrieveByUsername(username);
        return mapper.map(userEntity, User.class);
    }

    @Override
    public List<String> retrieveAllId() {
        return userDao.retrieveAllId();
    }

    @Override
    public int update(User dto) {
        return userDao.update(mapper.map(dto, UserEntity.class));
    }

    @Override
    public int deactivateById(User dto) {
        return userDao.deactivateById(mapper.map(dto, UserEntity.class));
    }

    @Override
    public List<String> retrieveByEmail(String email) {
        return userDao.retrieveByEmail(email);
    }

    @Override
    public int resetPassword(String email, String password) {
        return userDao.resetPassword(email, password);
    }
}
