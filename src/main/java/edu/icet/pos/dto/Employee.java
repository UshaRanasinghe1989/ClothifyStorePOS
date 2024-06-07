package edu.icet.pos.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Employee {
    private String id;
    private String name;
    private Date dob;
    private String nic;
    private String contact_no;
    private String email;
    private String address;
}
