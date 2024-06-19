package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CustomerBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CustomerDao;
import edu.icet.pos.dto.Category;
import edu.icet.pos.dto.Customer;
import edu.icet.pos.entity.CustomerEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean save(Customer dto) {
        return customerDao.save(new ModelMapper().map(dto, CustomerEntity.class));
    }

    @Override
    public int update(Customer dto) {
        return customerDao.update(new ModelMapper().map(dto, CustomerEntity.class));
    }

    @Override
    public Customer retrieveById(String id) {
        return null;
    }

    @Override
    public Customer retrieveByContactNo(String contactNo) {
        List<CustomerEntity> customerEntityList = customerDao.retrieveByContactNo(contactNo);
        Customer customer=null;
        if(!customerEntityList.isEmpty()){
           customer = new ModelMapper().map(customerEntityList.get(0), Customer.class);
        }

        return customer;
    }
}
