package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.entity.OrderEntity;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderEntity entity) {
        return false;
    }

    @Override
    public List retrieveAll() {
        return null;
    }

    @Override
    public List retrieveById(String id) {
        return null;
    }

    @Override
    public List retrieveAllId() {
        return null;
    }

    @Override
    public int update(OrderEntity entity) {
        return 0;
    }
}
