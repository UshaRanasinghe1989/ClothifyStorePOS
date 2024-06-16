package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString
@Slf4j
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    private String id;

    //AN ORDER MAY HAVE MANY PRODUCTS
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_detail",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<ProductEntity> productEntitySet = new HashSet<>();
    public Set<ProductEntity> getProduct(){
        return productEntitySet;
    }

    @Column(name = "no_products", nullable = false)
    private int noOfProducts;

    //ONE ORDER SHOULD HAVE ONE SELLER
    //private String sellerId;

    //ONE ORDER SHOULD HAVE ONE CASHIER
    //private String cashierId;


    @Column(name = "gross_amount", nullable = false)
    private double grossAmount;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "net_amount", nullable = false)
    private double netAmount;

    //private int terminalId;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    public OrderEntity(String id){
        this.id = id;
    }

    OrderEntity(int noOfProducts, double grossAmount, double discount, double netAmount, Date orderDate){
        this.noOfProducts = noOfProducts;
        this.grossAmount = grossAmount;
        this.discount = discount;
        this.netAmount = netAmount;
        this.orderDate = orderDate;
    }

    public void addProduct(ProductEntity productEntity){
        this.productEntitySet.add(productEntity);
        log.info(productEntitySet.toString()+"*****");
    }


}
