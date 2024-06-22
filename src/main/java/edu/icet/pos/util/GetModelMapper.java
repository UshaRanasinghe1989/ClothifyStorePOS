package edu.icet.pos.util;

import org.modelmapper.ModelMapper;

public class GetModelMapper {
    private static GetModelMapper instance;

    private GetModelMapper(){}

    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    public static GetModelMapper getInstance(){
        if (instance==null){
            instance = new GetModelMapper();
        }
        return instance;
    }
}
