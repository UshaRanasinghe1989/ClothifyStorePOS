package edu.icet.pos.dto;

import jakarta.persistence.Id;
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
    private String empId;
    private String systemName;
    private String email;
    private String password;
    private String type;
    private boolean isActive;
    private Date createDate;
}
