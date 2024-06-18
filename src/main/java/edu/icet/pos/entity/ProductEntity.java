package edu.icet.pos.entity;

import edu.icet.pos.dto.Orders;
import edu.icet.pos.util.ProductSizes;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column(name = "id")
    private String id;

    /*@ManyToMany(mappedBy = "productEntitySet")
    private Set<OrderEntity> orderEntitySet = new HashSet<>();
    public Set<OrderEntity> getOrders(){
        return orderEntitySet;
    }*/
    @OneToMany(mappedBy = "productEntity", fetch = FetchType.EAGER)
    private Set<OrderDetailEntity> orderDetailEntitySet = new HashSet<>();

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
    
    @Column
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductSizes size;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDateTime;

    //A PRODUCT MAY HAVE MANY PRODUCT IMAGES
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ProductImageEntity> productImageEntityList;

    //A PRODUCT MAY HAVE MANY STOCKS
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.MERGE)
    private List<StockEntity> stockEntityList;

    public ProductEntity(String id, CategoryEntity categoryEntity, SupplierEntity supplierEntity, String name, String description, ProductSizes size, Date createDateTime, List<ProductImageEntity> productImageEntityList, List<StockEntity> stockEntityList) {
        this.id = id;
        this.categoryEntity = categoryEntity;
        this.supplierEntity = supplierEntity;
        this.name = name;
        this.description = description;
        this.size = size;
        this.createDateTime = createDateTime;
        this.productImageEntityList = productImageEntityList;
        this.stockEntityList = stockEntityList;
    }

    public void addOrderDetail(OrderDetailEntity orderDetailEntity){
        this.orderDetailEntitySet.add(orderDetailEntity);
    }
}
