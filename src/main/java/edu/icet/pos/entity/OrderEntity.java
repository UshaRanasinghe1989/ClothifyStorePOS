package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Slf4j
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    private String id;

    //AN ORDER MAY HAVE MANY PRODUCTS
    /*@ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "order_detail",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<ProductEntity> productEntitySet = new HashSet<>();
    public Set<ProductEntity> getProduct(){
        return productEntitySet;
    }*/
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetailEntity> orderDetailEntitySet = new HashSet<>();

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

    public void addOrderDetail(OrderDetailEntity orderDetailEntity){
        this.orderDetailEntitySet.add(orderDetailEntity);
    }
}
