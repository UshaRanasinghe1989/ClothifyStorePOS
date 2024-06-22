package edu.icet.pos.dao;

import edu.icet.pos.dao.custom.impl.*;
import edu.icet.pos.util.DaoType;

public class DaoFactory implements SuperDao {
    private static DaoFactory instance;

    private DaoFactory(){}
    public static DaoFactory getInstance(){
        return instance==null?(instance=new DaoFactory()):instance;
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case EMPLOYEE: return (T) new EmployeeDaoImpl();
            case USER: return (T) new UserDaoImpl();
            case CATEGORY: return (T) new CategoryDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case PRODUCT: return (T) new ProductDaoImpl();
            case STOCK: return (T) new StockDaoImpl();
            case ORDERS: return (T) new OrderDaoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailDaoImpl();
            case CUSTOMER: return (T) new CustomerDaoImpl();
            case PAYMENT: return (T) new PaymentDaoImpl();
            case RETURN: return (T) new ReturnDaoImpl();
            case DAMAGED_STOCK:return (T) new DamagedStockDaoImpl();
        }
        return null;
    }
}
