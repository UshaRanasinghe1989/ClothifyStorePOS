package edu.icet.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Supplier {
    private String id;
    private String name;
    private String contactPerson;
    private String contactNo;
    private String email;
    private String address;
}
