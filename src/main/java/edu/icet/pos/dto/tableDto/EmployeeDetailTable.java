package edu.icet.pos.dto.tableDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeDetailTable {
    private String empId;
    private String name;
    private LocalDate dob;
    private String nic;
    private String contactNo;
    private String email;
    private String address;
}
