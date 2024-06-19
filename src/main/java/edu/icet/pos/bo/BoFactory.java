package edu.icet.pos.bo;

import edu.icet.pos.bo.custom.impl.*;
import edu.icet.pos.dao.custom.impl.CategoryDaoImpl;
import edu.icet.pos.util.BoType;

public class BoFactory implements SuperBo{
    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance!=null?instance:(instance=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case EMPLOYEE : return (T) new EmployeeBoImpl();
            case USER: return (T) new UserBoImpl();
            case CATEGORY: return (T) new CategoryBoImpl();
            case SUPPLIER: return (T) new SupplierBoImpl();
            case PRODUCT: return (T) new ProductBoImpl();
            case STOCK: return (T) new StockBoImpl();
            case ORDERS: return (T) new OrderBoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
            case CUSTOMER: return (T) new CustomerBoImpl();
        }
        return null;
    }
}
