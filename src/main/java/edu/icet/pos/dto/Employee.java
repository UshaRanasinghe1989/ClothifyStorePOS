package edu.icet.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private String id;
    private String title;
    private String initials;
    private String name;
    private LocalDate dob;
    private String nic;
    private String gender;
    private String maritalStatus;
    private String contactNo;
    private String email;
    private String address;
    private Date createdDate;
}
