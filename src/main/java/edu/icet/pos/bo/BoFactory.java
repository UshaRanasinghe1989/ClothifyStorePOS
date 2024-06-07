package edu.icet.pos.bo;

import edu.icet.pos.bo.custom.impl.EmployeeBoImpl;
import edu.icet.pos.bo.custom.impl.UserBoImpl;
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
        }
        return null;
    }
}
