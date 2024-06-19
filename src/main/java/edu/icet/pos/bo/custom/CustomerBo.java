package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Customer;
import edu.icet.pos.entity.CustomerEntity;

import java.util.List;

public interface CustomerBo extends SuperBo {
    boolean save(Customer dto);
    int update(Customer dto);
    Customer retrieveById(String id);
    Customer retrieveByContactNo(String contactNo);
}
