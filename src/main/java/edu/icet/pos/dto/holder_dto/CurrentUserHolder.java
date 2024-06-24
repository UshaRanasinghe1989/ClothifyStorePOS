package edu.icet.pos.dto.holder_dto;

import edu.icet.pos.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserHolder {
    private User user;
    private static CurrentUserHolder instance;

    private CurrentUserHolder(){}

    public static CurrentUserHolder getInstance(){
        return instance!=null?instance:(instance=new CurrentUserHolder());
    }
}
