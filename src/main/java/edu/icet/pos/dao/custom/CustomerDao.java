package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.CustomerEntity;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity> {
    List<CustomerEntity> retrieveByContactNo(String contactNo);
}
