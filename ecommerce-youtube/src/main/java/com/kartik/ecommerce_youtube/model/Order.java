package com.kartik.ecommerce_youtube.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String orderId;


//    one user have many orders but one order have only one user
    @ManyToOne
    private User user;


//    one order hve many items like watcgh,earphone,
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();


    private LocalDateTime orderDate;


    private LocalDateTime deliveryDate;

//    address relation -one tonone one order deliver to one address not many address
    @OneToOne
    private Addres shippingAddress;


    @Embedded
    private PaymentDetails paymentDetails=new PaymentDetails();

    private double totalPrice;

    private Integer totalDiscountedPrice;

    private Integer discount;

    private String OrderStatus;

    private int totalItems;

    private LocalDateTime createdAt;






}
