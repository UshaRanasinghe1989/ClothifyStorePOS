package edu.icet.pos.dto;

import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String id;
    private EmployeeEntity employeeEntity;
    private String systemName;
    private String email;
    private String password;
    private UserType type;
    private Boolean isActive;
    private Date createDate;

    public User(String id, String systemName, String password, UserType type) {
        this.id = id;
        this.systemName = systemName;
        this.password = password;
        this.type = type;
    }

    public User(String id, boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }
}
