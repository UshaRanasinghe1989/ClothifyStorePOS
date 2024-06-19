package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contactNo;
    private String name;
    private String email;

    CustomerEntity(String contactNo, String name, String email){
        this.contactNo = contactNo;
        this.name = name;
        this.email = email;
    }

    CustomerEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
