package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    private String id;

    //ONE PRODUCT BELONGS TO ONE CATEGORY
    //FOREIGN KEY REFERS CATEGORY TABLE
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    //ONE PRODUCT WOULD BE SUPPLIED BY ONE SUPPLIER
    //FOREIGN KEY REFERS SUPPLIER TABLE
    @ManyToOne
    @JoinColumn(name = "supplier_id",nullable = false)
    private SupplierEntity supplierEntity;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDateTime;

    //A PRODUCT MAY HAVE MANY PRODUCT IMAGES
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ProductImageEntity> productImageEntityList;

    //A PRODUCT MAY HAVE MANY STOCKS
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.MERGE)
    private List<StockEntity> stockEntityList;
}
