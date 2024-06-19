package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Customer;

public interface CustomerBo extends SuperBo {
    boolean save(Customer dto);
    int update(Customer dto);
    Customer retrieveById(String id);
    Customer retrieveByContactNo(String contactNo);
}
