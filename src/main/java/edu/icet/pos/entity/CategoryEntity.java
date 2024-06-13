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
@Table(name = "product_category")
public class CategoryEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    //A CATEGORY MAY HAVE MANY PRODUCTS
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.MERGE)
    private List<ProductEntity> productEntityList;
}
