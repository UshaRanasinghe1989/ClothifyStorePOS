package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CustomerBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CustomerDao;
import edu.icet.pos.dto.Customer;
import edu.icet.pos.entity.CustomerEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;

import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean save(Customer dto) {
        return customerDao.save(mapper.map(dto, CustomerEntity.class));
    }

    @Override
    public int update(Customer dto) {
        return customerDao.update(mapper.map(dto, CustomerEntity.class));
    }

    @Override
    public Customer retrieveById(String id) {
        List<CustomerEntity> customerEntityList = customerDao.retrieveById(id);

        return mapper.map(customerEntityList.get(0), Customer.class);
    }

    @Override
    public Customer retrieveByContactNo(String contactNo) {
        List<CustomerEntity> customerEntityList = customerDao.retrieveByContactNo(contactNo);
        Customer customer=null;
        if(!customerEntityList.isEmpty()){
           customer = mapper.map(customerEntityList.get(0), Customer.class);
        }

        return customer;
    }
}
