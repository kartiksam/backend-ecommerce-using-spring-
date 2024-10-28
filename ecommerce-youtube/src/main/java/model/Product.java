package model;

import jakarta.persistence.*;
import jdk.jfr.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private int price;

    @Column(name="discounted_price")
    private int discountedPrice;

    @Column(name="discount_persent")
    private int discountPersent;

    @Column(name = "quantity")
    private int quantity;

    private String brand;

    private String color;

    @Embedded
    @ElementCollection
    @Column(name="sizes")
    private Set<Sizee> sizes=new HashSet<>();

    @Column(name="image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating> ratings=new ArrayList<>();


//    one product can have multi reviews
    @OneToMany
    private List<Review> reviews=new ArrayList<>();

    @Column(name="num_ratings")
    private int numRatings;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;
















}
