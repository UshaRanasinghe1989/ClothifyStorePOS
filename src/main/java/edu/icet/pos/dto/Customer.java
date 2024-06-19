package edu.icet.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private int id;
    private String contactNo;
    private String name;
    private String email;

    public Customer(String contactNo, String name, String email){
        this.contactNo = contactNo;
        this.name = name;
        this.email = email;
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
