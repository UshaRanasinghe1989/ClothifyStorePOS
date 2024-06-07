package edu.icet.pos.dao;

import edu.icet.pos.dao.custom.impl.EmployeeDaoImpl;
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
        }
        return null;
    }
}
