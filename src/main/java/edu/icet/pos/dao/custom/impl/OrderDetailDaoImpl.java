package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.entity.OrderDetailEntity;

import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean save(OrderDetailEntity entity) {
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
    public int update(OrderDetailEntity entity) {
        return 0;
    }
}
