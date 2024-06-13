package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contactPerson;
    @Column(nullable = false)
    private String contactNo;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String address;
    //ONE SUPPLIER MAY SUPPLY MANY PRODUCTS
    @OneToMany(mappedBy = "supplierEntity", cascade = CascadeType.MERGE)
    private List<ProductEntity> productEntityList;
}
